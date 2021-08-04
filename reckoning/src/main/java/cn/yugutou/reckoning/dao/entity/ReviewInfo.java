package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReviewInfo implements Serializable {
    private Long reviewId;
    private Integer reviewerId;
    private Integer billingId;
    private Date reviewTime;
    private String reviewStatus;
    private String rejectionReason;
    private Date createTime;
    private Date modifyTime;

}