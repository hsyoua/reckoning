package cn.yugutou.reckoning.service;

import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;

import java.util.List;

public interface BillService {
    /**
     * 新增账单
     */
    boolean addBill(BillReq billReq);

    /**
     * 新增账单用户关联信息
     */
    boolean addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs);
}
