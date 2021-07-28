package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
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
}