package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.ReviewMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.req.QueryBillingInfoReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.dto.resp.QueryBillingInfoResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("BillService")
@Slf4j
@Transactional
public class BillServiceImpl implements BillService {

    @Autowired
    private BillingMapper billingMapper;

    @Autowired
    private UserBillAssociationMapper userBillAssociationMapper;

    @Autowired
    private UserMapper userMapper;


    private final static String DEFAULT_PAYMENT_STATUS = "01";
    private final static String DEFAULT_BILLING_STATUS = "01";

    @Override
    public Long addBill(BillReq billReq) {
        BillingInfo billingInfo = new BillingInfo();
        BeanUtils.copyProperties(billReq, billingInfo);
        //获取账单id
        Long billid = NumberGenerator.getNumber(12);



        billingInfo.setBillingId(billid);
        billingInfo.setBillingStatus(DEFAULT_BILLING_STATUS);
        billingMapper.addBill(billingInfo);
        return billid;
    }

    @Override
    public Long addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs, Long billid, BigDecimal amount) {
        ArrayList<UserBillAssociation> userBillAssociations = new ArrayList<>();
        //calculate apportionment amount
        /*每人均摊*/
        BigDecimal apportionedAmount = amount.divide(BigDecimal.valueOf(userBillAssociationReqs.size()));
        log.info("default allocation method is 'AA' ，apportioned amount is : {}", apportionedAmount);
        //check participating user information
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            //入参的用户id和用户参与类型不能为空
            Long userId = userBillAssociationReq.getUserId();
            if (userId == null || userBillAssociationReq.getUserParticipationType() == null) {
                throw new CustomException(ResultCode.BILL_PARAMETER_BE_EMPTY);
            }
            //判断入参的用户id在用户表是否存在
            UsrInfo usrInfo = userMapper.queryUserDetail(userId);
            if (usrInfo == null) {
                return userId;
            }

            UserBillAssociation userBillAssociation = new UserBillAssociation();
            BeanUtils.copyProperties(userBillAssociationReq, userBillAssociation);
            long userbillid = NumberGenerator.getNumber(12);
            userBillAssociation.setAssociationId(userbillid);
            userBillAssociation.setApportionedAmount(apportionedAmount);
            userBillAssociation.setBillingId(billid);
            userBillAssociation.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
            userBillAssociations.add(userBillAssociation);
        }
        log.info("userBillAssociation final save list [{}]", userBillAssociations);
        userBillAssociationMapper.addUserBillAssociation(userBillAssociations);
        return null;
    }

    @Override
    public List<QueryBillDetailResp> findBillDetail(QueryBillDetailReq req) {

        Long billingId = Long.valueOf(req.getBillingId());

        //获取查询信息
        List<QueryBillDetailResp> billDetails = billingMapper.findBillDetail(req);
        /*如果查询为0，则用户未参与该账单，无法查询*/
        if (billDetails.size() == 0) {
            throw new CustomException(ResultCode.FIND_BILL_DETAIL_ERROR);
        }
        ;

        return billDetails;
    }


    @Override
    public QueryBillingInfoResp queryUserBillingInfo(QueryBillingInfoReq queryBillingInfoReq) {
        Integer pageNo = queryBillingInfoReq.getPageNo();
        Integer pageSize = queryBillingInfoReq.getPageSize();
        if (pageSize > 30) {
            throw new CustomException(ResultCode.BILL_PAGESIZE_MAX);
        }

        Page page = PageHelper.startPage(pageNo, pageSize);
        log.info("queryUserBillingInfo方法的page信息:", page.getPageNum() + page.getPageSize());
    /*    List<QueryUserResp> queryUserResps = billingMapper.queryUserByNamePhone(queryBillingInfoReq);
        QueryUserAndTotalResp queryUserAndTotalResp =   new QueryUserAndTotalResp();
        queryUserAndTotalResp.setTotalNum(page.getTotal());
        queryUserAndTotalResp.setUserInfoByNamePhone(queryUserResps);*/
        return null;
    }


}
