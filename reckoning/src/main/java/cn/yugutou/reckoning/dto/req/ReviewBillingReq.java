package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 审核账单请求参数封装
 */
@Data
public class ReviewBillingReq implements Serializable {
    private Long reviewId;
    private Long reviewerId;
    private String reviewStatus;
    private String rejectionReason;
}
