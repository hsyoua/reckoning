package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("BillService")
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
    private BillingMapper billingMapper;

    @Autowired
    private UserBillAssociationMapper userBillAssociationMapper;

    private final static String DEFAULT_PAYMENT_STATUS = "01";
    private final static String DEFAULT_BILLING_STATUS = "01";

    @Override
    public Long addBill(BillReq billReq) {
        BillingInfo billingInfo = new BillingInfo();
        BeanUtils.copyProperties(billReq, billingInfo);
        //获取账单id
        long billid = NumberGenerator.getNumber(12);

        billingInfo.setBillingId(billid);
        billingInfo.setBillingStatus(DEFAULT_BILLING_STATUS);
        billingMapper.addBill(billingInfo);
        return billid;
    }

    @Override
    public boolean addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs, Long billid, BigDecimal amount) {
        ArrayList<UserBillAssociation> userBillAssociations = new ArrayList<>();
        //calculate apportionment amount
        /*每人均摊*/
        BigDecimal apportionedAmount = amount.divide(BigDecimal.valueOf(userBillAssociationReqs.size()));
        log.info("default allocation method is 'AA' ，apportioned amount is : {}", apportionedAmount);
        //check participating user information
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            if (userBillAssociationReq.getUserId() == null || userBillAssociationReq.getUserParticipationType() == null) {
                throw new CustomException(ResultCode.BILL_PARAMETER_BE_EMPTY);
            }
            UserBillAssociation userBillAssociation = new UserBillAssociation();
            BeanUtils.copyProperties(userBillAssociationReq, userBillAssociation);
            long userbillid = NumberGenerator.getNumber(12);
            userBillAssociation.setAssociationId(userbillid);
            userBillAssociation.setApportionedAmount(apportionedAmount);
            userBillAssociation.setBillingId(billid);
            userBillAssociation.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
            userBillAssociations.add(userBillAssociation);
        }
        log.info("userBillAssociation final save list [{}]", userBillAssociations);
        return userBillAssociationMapper.addUserBillAssociation(userBillAssociations);
    }

    @Override
    public List<QueryBillDetailResp> findBillDetail(QueryBillDetailReq req) {

        Long billingId = Long.valueOf(req.getBillingId());

        //获取查询信息
        List<QueryBillDetailResp> billDetails = billingMapper.findBillDetail(req);
        /*如果查询为0，则用户未参与该账单，无法查询*/
        if (billDetails.size() == 0) {
            throw new CustomException(ResultCode.FIND_BILL_DETAIL_ERROR);
        }
        ;

        return billDetails;
    }





}
