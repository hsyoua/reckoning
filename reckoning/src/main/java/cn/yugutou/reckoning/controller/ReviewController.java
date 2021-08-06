package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.dto.req.FindReviewListByIdReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.interceptor.RequestContext;
import cn.yugutou.reckoning.service.ReviewService;
import cn.yugutou.reckoning.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/review")
public class ReviewController {

     @Autowired
     private ReviewService reviewService;

    @PostMapping (value = "/findReviewListById", produces = "application/json;charset=UTF-8")
    public Result findReviewListById(@RequestBody FindReviewListByIdReq findReviewListByIdReq ){
        List<ReviewWaitingResp> reviewWaiting = reviewService.findReviewWaiting( findReviewListByIdReq.getUserId());
        return Result.success(reviewWaiting);
    }

}
