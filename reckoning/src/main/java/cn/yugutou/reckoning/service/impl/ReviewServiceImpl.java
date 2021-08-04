package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dao.mapper.ReviewMapper;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ReviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {
     @Autowired
     private ReviewMapper reviewMapper;

    @Override
    public boolean addReviewInfo(ReviewInfo reviewInfo) {
        //生成审核表id
        Long reviewId = NumberGenerator.getNumber(12);
        reviewInfo.setReviewId(reviewId);
        reviewInfo.setReviewStatus("00");
        return reviewMapper.addReviewInfo(reviewInfo);
    }
}
