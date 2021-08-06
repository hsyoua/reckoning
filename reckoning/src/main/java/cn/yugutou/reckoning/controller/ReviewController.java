package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.req.UpdateUserInfoReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/review")
public class ReviewController {

     @Autowired
     private ReviewService reviewService;

    /**
     * 查询待审核账单列表
     * @param id
     * @return
     */
    @GetMapping (value = "/findReviewListById", produces = "application/json;charset=UTF-8")
    public Result findReviewListById( Long id){
        List<ReviewWaitingResp> reviewWaiting = reviewService.findReviewWaiting(id);

        if(reviewWaiting.size() >0){
            return Result.success(reviewWaiting);
        }
        return Result.failure();
    }

    /**
     * 审核账单(待完成)
     * @param req
     * @return
     */
    @PostMapping (value = "/reviewBilling", produces = "application/json;charset=UTF-8")
    public Result reviewBillingController(@RequestBody ReviewBillingReq req){

        return null;
    }


}
