package cn.yugutou.reckoning.dao.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("reviewInfo")
public class ReviewInfo implements Serializable {
    private Long reviewId;
    private Long reviewerId;
    private Long billingId;
    private Date reviewTime;
    private String reviewStatus;
    private String rejectionReason;
    private Date createTime;
    private Date modifyTime;

}