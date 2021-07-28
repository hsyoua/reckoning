package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.AddBillResp;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@Transactional
@RequestMapping("/billing")
public class BillingController {
   @Autowired
   private BillService billService;

    @PostMapping(value = "/addBill",produces = "application/json;charset=UTF-8")
    public ResponseEntity<LoginResp> login(@RequestBody BillReq billReq){

        List<UserBillAssociationReq> userBillAssociationReqs = billReq.getUserBillAssociationReqs();

        /**
         * 增加金额校验，账单金额需要等于各用户分摊金额之和
         */
      /*  BigDecimal amount = billReq.getAmount();

        BigDecimal amount_user_sum=new BigDecimal(0);
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            BigDecimal apportionedAmount = userBillAssociationReq.getApportionedAmount();
            amount_user_sum=amount_user_sum.add(apportionedAmount);
        }
        if (amount.compareTo(amount_user_sum)!=0){
            log.info("账单金额和用户分摊金额之和不等");
            throw new CustomException(ResultCode.USER_BILL_AMOUNT);
        }*/
        boolean result= billService.addBill(billReq);
        billService.addUserBillAssociation(userBillAssociationReqs);





        AddBillResp resp = new AddBillResp();
        resp.setResult(result);
        if (!result){
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(resp,HttpStatus.OK);
    }

}
