package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssciotionEntity;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BillService")
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
    private BillingMapper billingMapper;

    @Autowired
    private UserBillAssociationMapper userBillAssociationMapper;

    //定义账单id
    Long billid;

    @Override
    public boolean addBill(BillReq billReq) {
        BillingInfo billingInfo = new BillingInfo();
        BeanUtils.copyProperties(billReq,billingInfo);
        //获取账单id
         billid = NumberGenerator.getNumber(9);
        billingInfo.setBillingId(billid);
      log.info("billingInfo的值"+billingInfo);
        return billingMapper.addBill(billingInfo);
    }

    @Override
    public boolean addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs) {
        ArrayList<UserBillAssciotionEntity> userBillAssciotionEntities = new ArrayList<>();
        //获取账单用户关联表id

        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            UserBillAssciotionEntity assciotionEntity = new UserBillAssciotionEntity();
            BeanUtils.copyProperties(userBillAssociationReq,assciotionEntity);
            long userbillid = NumberGenerator.getNumber(9);
            assciotionEntity.setAssociationId(userbillid);
            assciotionEntity.setBillingId(billid);
            userBillAssciotionEntities.add(assciotionEntity);
            log.info("assciotionEntity.getUserId()的值"+assciotionEntity.getUserId());
        }




        return userBillAssociationMapper.addUserBillAssociation(userBillAssciotionEntities);
    }


}
