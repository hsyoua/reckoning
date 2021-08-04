package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.ReviewInfo;
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
     *
     * @return
     */
    List<ReviewWaitingResp> findReviewWaiting(Long id);
}
