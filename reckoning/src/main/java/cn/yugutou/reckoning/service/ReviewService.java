package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.ReviewInfo;

public interface ReviewService {

    /**
     * 增加审核表信息
     * @param reviewInfo
     * @return
     */
    boolean addReviewInfo(ReviewInfo reviewInfo);
}
