package cn.yugutou.reckoning.dto.resp;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Alias("consumeDetailResp")
public class ConsumeDetailResp implements Serializable {
    private Long userId;
    private String userName;
    private String userParticipationType;
    private BigDecimal apportionedAmount;
    private String paymentStatus;
}
