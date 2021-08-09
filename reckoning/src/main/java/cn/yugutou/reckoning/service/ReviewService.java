package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dto.req.ReviewBillingReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewService {

    /**
     * 增加审核表信息
     * @param reviewInfo
     * @return
     */
    boolean addReviewInfo(ReviewInfo reviewInfo);

    /**
     *查看待审核账单
     * @return
     */
    List<ReviewWaitingResp> findReviewWaiting(Long id);

    /**
     * 审核账单
     * @param req
     * @return
     */
    boolean updateReviewStatus(ReviewBillingReq req);

}
