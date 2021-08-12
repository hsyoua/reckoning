package cn.yugutou.reckoning.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Alias("queryBillDetailReq")
public class QueryBillDetailReq implements Serializable {
    @NotNull(message = "The userId   cannot be empty")
    private Long userId;
    @NotNull(message = "The BillingId   cannot be empty")
    private Long BillingId;


}
