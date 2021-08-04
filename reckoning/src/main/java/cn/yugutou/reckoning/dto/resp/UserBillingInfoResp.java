package cn.yugutou.reckoning.dto.resp;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
@Data
@Alias("userBillingInfoResp")
public class UserBillingInfoResp {
    //账单ID
    private Integer billingId;
    //金额
    private Integer amount;
    //账单主题
    private String billTheme;
    //人数
    private Integer peopleNum;
    //账单状态
    private String billingStatus;
    //消费时间
    private Date dissipate;
}