package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface BillingMapper {
    /**
     * 增加账单信息
     * @param billingInfo
     * @return
     */
    boolean addBill(BillingInfo billingInfo);

    /**
     * 查询账单详细信息
     * @param req
     * @return
     */
    List<QueryBillDetailResp> findBillDetail(QueryBillDetailReq req);
}
