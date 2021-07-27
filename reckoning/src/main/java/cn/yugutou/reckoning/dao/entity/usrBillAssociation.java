package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class usrBillAssociation {
    private Long associationId;
    private Long userId;
    private Long billingId;
    private String userParticipationType;
    private String paymentStatus;
    private Date createTime;
    private Date modifyTime;
}
