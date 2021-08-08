package cn.yugutou.reckoning.dto.req;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Alias("queryBillingInfoReq")
public class QueryBillingInfoReq implements Serializable {
    //用户ID
    private Long userId;
    //开始消费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dissipateTimeStart;
  //  结束消费时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date dissipateTimeEnd;
    //用户参与类型
    private String userParticipationType;
    //关键字(账单主题)
    private String billTheme;
    //支付状态
    private String paymentStatus;
    //账单状态
    private String billingStatus;
    //分摊方式
    private String allocationMethod;
    //人数下限
    private Integer peopleNumMin;
    //人数上限
    private Integer peopleNumMax;
    //账单金额下限
    private BigDecimal amountMin;
    //账单金额上限
    private BigDecimal amountMax;
    //个人分摊金额下限
    private BigDecimal apportionedAmountMin;
    //个人分摊金额上限
    private BigDecimal apportionedAmountMax;
    //起始页数
    private Integer pageNo;
    //每页条数
    private Integer pageSize;
}
