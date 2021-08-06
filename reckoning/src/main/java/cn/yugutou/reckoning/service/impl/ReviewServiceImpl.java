package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.ReviewMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.ReviewBillingReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.interceptor.RequestContext;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ReviewService")
@Slf4j
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BillingMapper billingMapper;

    @Autowired
    private UserBillAssociationMapper userBillAssociationMapper;

    @Override
    public boolean addReviewInfo(ReviewInfo reviewInfo) {
        //生成审核表id
        Long reviewId = NumberGenerator.getNumber(12);
        reviewInfo.setReviewId(reviewId);
        reviewInfo.setReviewStatus("00");
        return reviewMapper.addReviewInfo(reviewInfo);
    }

    @Override
    public List<ReviewWaitingResp> findReviewWaiting(Long id) {
        //（用户只能查询自己作为审核人的待办账单任务，管理员可以查询所有待办账单任务
        UsrInfo usrInfo = userMapper.queryUserDetail(id);
        if (usrInfo == null) {
            throw new CustomException(ResultCode.USER_NOT_EXISTS);
        }
        //获取用户身份
        String userRole = usrInfo.getUserRole();
        //check login userId
        String userIdOfToken = JWTUtil.getUserId(RequestContext.get());
        if ("01".equals(userRole)) {
            id = null;
        } else if (id != Long.parseLong(userIdOfToken)) {
            id = Long.parseLong(userIdOfToken);
        }
        return reviewMapper.findReviewWaiting(id);
    }

    @Override
    public boolean updateReviewStatus(ReviewBillingReq req) {
        String reviewStatus = req.getReviewStatus();
        String rejectionReason = req.getRejectionReason();
        Long reviewId = req.getReviewId();
        //获取需要审核的账单id
        ReviewInfo reviewinfo = reviewMapper.findReviewInfoById(reviewId);
        Long billingId = reviewinfo.getBillingId();
        BillingInfo billingInfo = new BillingInfo();

        if ("02".equals(reviewStatus) && (rejectionReason == null || rejectionReason == "")) {
            throw new CustomException(ResultCode.FAILURE);
        }
        boolean asso = false;
        billingInfo.setBillingId(billingId);
        //审核通过,账单由待结算变成结算中状态，用户账单关联信息分摊者用户支付状态为待支付状态，审核状态为审核通过。
        if ("01".equals(reviewStatus)) {
            billingInfo.setBillingStatus("02");
            //修改支付状态
            UserBillAssociation association = new UserBillAssociation();
            association.setBillingId(billingId);
            association.setPaymentStatus("01");
            asso = userBillAssociationMapper.updateStatusByBillingId(association);
            if (!asso){
                throw new CustomException(ResultCode.FAILURE);
            }
        } else {//审核拒绝
            billingInfo.setBillingStatus("03");
        }
        //更新账单表状态
        boolean bill = billingMapper.updateBillingStatus(billingInfo);
        boolean review=reviewMapper.updateReviewStatus(req);


        return asso && bill && review ? true :false ;
    }
}
