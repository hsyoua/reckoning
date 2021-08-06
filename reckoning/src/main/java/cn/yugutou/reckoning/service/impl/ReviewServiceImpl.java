package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.ReviewMapper;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.ReviewBillingReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ReviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {
     @Autowired
     private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

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
        //获取用户身份
        String userRole = usrInfo.getUserRole();
        if ("01".equals(userRole)){
            id = null;
        }

        return reviewMapper.findReviewWaiting(id);
    }

    @Override
    public boolean updateReviewStatus(ReviewBillingReq req) {




        return false;
    }
}
