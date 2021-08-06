package cn.yugutou.reckoning.dto.req;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 审核账单请求参数封装
 */
@Data
@Alias("reviewBillingReq")
public class ReviewBillingReq implements Serializable {
    private Long reviewId;
    private Long reviewerId;
    private String reviewStatus;
    private String rejectionReason;
}
