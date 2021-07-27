package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillingInfo {
    private Long billingId;
    private BigDecimal amount;
    private Integer peopleNum;
    private String billingStatus;
    private String allocationMethod;
    private String apportionedAmount;
    private String consumptionNotes;
    private String consumerAddress;
    private Date dissipate;
    private Date createTime;
    private Date modifyTime;

}