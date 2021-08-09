package cn.yugutou.reckoning.dao.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Alias("billingInfo")
public class BillingInfo {
    private Long billingId;
    private BigDecimal amount;
    private Integer peopleNum;
    private String billingStatus;
    private String allocationMethod;
    private String consumptionNotes;
    private String consumerAddress;
    private Date dissipate;
    private Date createTime;
    private Date modifyTime;
    private Long createUserId;
    private String billTheme;
}