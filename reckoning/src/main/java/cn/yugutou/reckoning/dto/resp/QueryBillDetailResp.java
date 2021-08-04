package cn.yugutou.reckoning.dto.resp;

import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Alias("queryBillDetailResp")
public class QueryBillDetailResp implements Serializable {

    private Long billingId;
    private String billTheme;
    private BigDecimal amount;
    private Integer peopleNum;
    private String billingStatus;
    private String allocationMethod;
    private String consumptionNotes;
    private String consumerAddress;
    private Long createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dissipate;

    /*消费用户信息集合*/
    private List<ConsumeDetailResp>  conSumeList;

}
