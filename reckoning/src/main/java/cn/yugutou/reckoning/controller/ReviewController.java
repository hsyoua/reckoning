package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/review")
public class ReviewController {

     @Autowired
     private ReviewService reviewService;


    @PostMapping (value = "/findReviewListById", produces = "application/json;charset=UTF-8")
    public Result findReviewListById(@RequestBody FindReviewListByIdReq findReviewListByIdReq ) {
        List<ReviewWaitingResp> reviewWaiting = reviewService.findReviewWaiting(findReviewListByIdReq.getUserId());
        return Result.success(reviewWaiting);
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
