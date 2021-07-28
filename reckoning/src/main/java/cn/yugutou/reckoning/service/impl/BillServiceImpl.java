package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private final  static String  DEFAULT_PAYMENT_STATUS = "01";

    @Override
    public Long addBill(BillReq billReq) {
        BillingInfo billingInfo = new BillingInfo();
        BeanUtils.copyProperties(billReq,billingInfo);
        //获取账单id
        Long billid = NumberGenerator.getNumber(12);
        billingInfo.setBillingId(billid);
        log.info("billingInfo的值"+billingInfo);
        billingMapper.addBill(billingInfo);
        return  billid;
    }

    @Override
    public boolean addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs,Long billid,BigDecimal amount) {
        ArrayList<UserBillAssociation> userBillAssociations = new ArrayList<>();
        //calculate apportionment amount
        BigDecimal apportionedAmount = amount.divide(BigDecimal.valueOf(userBillAssociationReqs.size()));
        log.info("default allocation method is 'AA' ，apportioned amount is : {}",apportionedAmount);
        //check participating user information
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            if(userBillAssociationReq.getUserId()==null || userBillAssociationReq.getUserParticipationType() == null){
                throw new CustomException(ResultCode.BILL_PARAMETER_BE_EMPTY);
            }
            UserBillAssociation userBillAssociation = new UserBillAssociation();
            BeanUtils.copyProperties(userBillAssociationReq,userBillAssociation);
            long userbillid = NumberGenerator.getNumber(12);
            userBillAssociation.setAssociationId(userbillid);
            userBillAssociation.setApportionedAmount(apportionedAmount);
            userBillAssociation.setBillingId(billid);
            userBillAssociation.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
            userBillAssociations.add(userBillAssociation);
        }
        log.info("userBillAssociation final save list [{}]",userBillAssociations);
        return userBillAssociationMapper.addUserBillAssociation(userBillAssociations);
    }


}
