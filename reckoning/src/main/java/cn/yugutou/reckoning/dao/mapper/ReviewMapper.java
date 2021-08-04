package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ReviewMapper  {

    boolean addReviewInfo(ReviewInfo review);

}
