package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.AddBillResp;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.dto.resp.QueryBillingInfoResp;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/billing")
@Transactional
public class BillingController {
   @Autowired
   private BillService billService;
    //增加账单
    @PostMapping(value = "/addBill",produces = "application/json;charset=UTF-8")
    public ResponseEntity<AddBillResp> login(@RequestBody BillReq billReq){
        System.out.println(billReq);
        long billid= billService.addBill(billReq);
        List<UserBillAssociationReq> userBillAssociationReqs = billReq.getUserBillAssociationReqs();
        BigDecimal amount = billReq.getAmount();
        log.info("controller 层 userid"+String.valueOf(userBillAssociationReqs.get(0)));
        billService.addUserBillAssociation(userBillAssociationReqs,billid,amount);
        AddBillResp resp = new AddBillResp(billid);

        return new ResponseEntity(resp,HttpStatus.OK);
    }

    //查询用户账单信息(未完成)
    @PostMapping(value = "/queryBilling",produces = "application/json;charset=UTF-8")
    public ResponseEntity<QueryBillingInfoResp> queryBilling(@RequestBody QueryBillingInfoReq queryBillingInfoReq){
        log.info("controller层queryBilling方法入参值："+queryBillingInfoReq);
        QueryBillingInfoResp queryBillingInfoResp = billService.queryUserBillingInfo(queryBillingInfoReq);
        return new ResponseEntity(queryBillingInfoResp,HttpStatus.OK);
    }


    /**
     * 账单详情查询
     * @param request
     * @return
     */
    @PostMapping (value = "/fingBillDetail",produces = "application/json;charset=UTF-8")
    public ResponseEntity  fingBillDetail(@RequestBody @Validated QueryBillDetailReq request){
        List<QueryBillDetailResp> billDetails = billService.findBillDetail(request);
        return new ResponseEntity(billDetails,HttpStatus.OK);

    }

}
