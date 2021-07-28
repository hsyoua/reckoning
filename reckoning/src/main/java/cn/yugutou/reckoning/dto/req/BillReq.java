package cn.yugutou.reckoning.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BillReq implements Serializable {
    @NotBlank(message = "The people_num name cannot be empty")
    private Integer peopleNum;
    @NotBlank(message = "The  amount cannot be empty")
    private BigDecimal amount;
    private String allocationMethod;
    private String consumptionNotes;
    private String consumerAddress;
    private Long createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dissipate;
    private String billTheme;
    private List<UserBillAssociationReq>  userBillAssociationReqs;
}
