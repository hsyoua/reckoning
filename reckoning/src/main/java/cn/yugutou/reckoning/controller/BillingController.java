package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.AddBillResp;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/billing")
public class BillingController {
   @Autowired
   private BillService billService;

    @PostMapping(value = "/addBill",produces = "application/json;charset=UTF-8")
    public ResponseEntity<LoginResp> login(@RequestBody BillReq billReq){
        boolean result= billService.addBill(billReq);
        List<UserBillAssociationReq> userBillAssociationReqs = billReq.getUserBillAssociationReqs();

       log.info("controller 层 userid "+String.valueOf(userBillAssociationReqs.get(0)));
        billService.addUserBillAssociation(userBillAssociationReqs);
        AddBillResp resp = new AddBillResp();
        resp.setResult(result);
        if (!result){
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(resp,HttpStatus.OK);
    }

}
