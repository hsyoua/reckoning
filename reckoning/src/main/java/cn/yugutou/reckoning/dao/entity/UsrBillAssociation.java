package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UsrBillAssociation {
    private Long associationId;
    private Long userId;
    private Long billingId;
    private String userParticipationType;
    private BigDecimal apportionedAmount;
    private String paymentStatus;
    private Date createTime;
    private Date modifyTime;
}
