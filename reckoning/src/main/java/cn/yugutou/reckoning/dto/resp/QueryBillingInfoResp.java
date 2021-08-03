package cn.yugutou.reckoning.dto.resp;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class QueryBillingInfoResp implements Serializable {
    //用户账单信息
    private List userBillingInfo;
    //用户账单总条数
    private Integer totals;
}
