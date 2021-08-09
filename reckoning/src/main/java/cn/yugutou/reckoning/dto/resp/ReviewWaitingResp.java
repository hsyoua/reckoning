package cn.yugutou.reckoning.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 *查询待审核列表响应实体类
 */
@Data
@Alias("reviewWaitingResp")
public class ReviewWaitingResp implements Serializable {

    private Long reviewId;
    private Long billingId;
    private String billTheme;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dissipate;

}
