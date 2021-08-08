package cn.yugutou.reckoning.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Alias("userBillingInfoResp")
public class UserBillingInfoResp {
    //账单ID
    private Long billingId;
    //金额
    private BigDecimal amount;
    //账单主题
    private String billTheme;
    //人数
    private Integer peopleNum;
    //账单状态
    private String billingStatus;
    //消费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dissipate;
}
