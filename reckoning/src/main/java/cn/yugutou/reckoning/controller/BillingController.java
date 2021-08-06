package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.AddBillResp;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.dto.resp.QueryBillingInfoResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.Result;
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

    @Autowired
    private ReviewService reviewService;

    //增加账单
    @PostMapping(value = "/addBill", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AddBillResp> login(@RequestBody BillReq billReq) {
        System.out.println(billReq);
        long billid = billService.addBill(billReq);
        List<UserBillAssociationReq> userBillAssociationReqs = billReq.getUserBillAssociationReqs();
        BigDecimal amount = billReq.getAmount();
        Long aLong = billService.addUserBillAssociation(userBillAssociationReqs, billid, amount);


        Result result = new Result();
        AddBillResp resp = new AddBillResp(billid);
        //用户id不存在抛异常
        if (aLong!=null){
            throw new CustomException(ResultCode.BILL_USERID_ERROR);
        }
        //新增需求，插入账单表同时，增加审核表
        ReviewInfo reviewInfo = new ReviewInfo();
        //获取入参审核人id
        Long reviewerId = billReq.getReviewerId();
        int flag =0;
        //对输入的审核人id校验，如果不在用户id中则报错
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
              if (userBillAssociationReq.getUserId().equals(reviewerId) ){
                  flag =1;
              }
        }

        if (flag ==0){
            throw new CustomException(ResultCode.BILL_REVIEW_INSERT);
        }
        reviewInfo.setBillingId(billid);
        reviewInfo.setReviewerId(reviewerId);
        reviewService.addReviewInfo(reviewInfo);
        if (resp != null) {
            result = Result.success(resp);
        } else {
            result = Result.failure();
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //查询用户账单信息(未完成)
    @PostMapping(value = "/queryBilling", produces = "application/json;charset=UTF-8")
    public ResponseEntity<QueryBillingInfoResp> queryBilling(@RequestBody QueryBillingInfoReq queryBillingInfoReq) {
        log.info("controller层queryBilling方法入参值：" + queryBillingInfoReq);
        QueryBillingInfoResp queryBillingInfoResp = billService.queryUserBillingInfo(queryBillingInfoReq);
        return new ResponseEntity(queryBillingInfoResp, HttpStatus.OK);
    }


    /**
     * 账单详情查询
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/fingBillDetail", produces = "application/json;charset=UTF-8")
    public ResponseEntity fingBillDetail(@RequestBody @Validated QueryBillDetailReq request) {
        QueryBillDetailResp billDetails = billService.findBillDetail(request);
        Result result = null;
        if (billDetails != null) {
            result = Result.success(billDetails);
        } else {
            result = Result.failure();
        }
        return new ResponseEntity(result, HttpStatus.OK);

    }

}
