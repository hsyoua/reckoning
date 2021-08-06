package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dto.req.ReviewBillingReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReviewMapper  {
    /**
     * 新增账单时同步插入审核表
     * @param review
     * @return
     */
    boolean addReviewInfo(ReviewInfo review);


    /**
     *查询待审核账单
     * @return
     */
    List<ReviewWaitingResp>  findReviewWaiting(@Param("userId") Long id);


    boolean updateReviewStatus(ReviewBillingReq req);



    ReviewInfo findReviewInfoById(@Param("reviewId") Long id);
}
