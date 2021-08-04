package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserBillAssociationReq {
        private Long userId;
    private String userParticipationType;

}
