package cn.yugutou.reckoning.service;

import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.req.QueryBillingInfoReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.dto.resp.QueryBillingInfoResp;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    /**
     * 新增账单
     */
    Long addBill(BillReq billReq);

    /**
     * 新增账单用户关联信息
     */
    Long addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs, Long billid, BigDecimal amount);

    /**
     * 查询账单详细信息
     * @param req
     * @return
     */
   QueryBillDetailResp findBillDetail(QueryBillDetailReq req);

    /**
     * 查询用户账单信息
     * */
    QueryBillingInfoResp queryUserBillingInfo(QueryBillingInfoReq queryBillingInfoReq);
}
