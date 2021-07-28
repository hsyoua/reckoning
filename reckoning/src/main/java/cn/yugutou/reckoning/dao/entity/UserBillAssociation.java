package cn.yugutou.reckoning.dao.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Alias("userBill")
public class UserBillAssociation {
    private Long associationId;
    private Long userId;
    private Long billingId;
    private String userParticipationType;
    private BigDecimal apportionedAmount;
    private String paymentStatus;
    private Date createTime;
    private Date modifyTime;
}
