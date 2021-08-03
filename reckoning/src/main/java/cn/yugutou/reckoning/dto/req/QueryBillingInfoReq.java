package cn.yugutou.reckoning.dto.req;



import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class QueryBillingInfoReq implements Serializable {
    //用户ID
    private Integer userId;
    //消费时间
    private Date dissipateTime;
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
    //人数
    private Integer peopleNum;
    //账单金额
    private Integer amount;
    //分摊金额
    private Integer apportionedAmount;
    //起始页数
    private Integer pageNo;
    //每页条数
    private Integer pageSize;
}
