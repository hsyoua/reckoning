package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.*;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.utils.Result;
import cn.yugutou.reckoning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",produces="application/json;charset=UTF-8")
    public Result<RegisterResp> registerUser(@RequestBody @Validated RegisterReq requset){
        boolean result = userService.registerUser(requset);
        RegisterResp resp = new RegisterResp(result);
        return Result.success(resp);
    }

    @PostMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public Result<LoginResp> login(@RequestBody @Validated LoginReq loginReq){
        log.info("login request is [{}]",loginReq);
        LoginResp loginResp = userService.login(loginReq);
        if(loginResp.getErrorNum()!=null){
            Result result = new Result();
            result.setCode(ResultCode.USER_LOGIN_CHECK_FAIL.getCode());
            result.setMessage("密码错误，剩余错误"+loginResp.getErrorNum()+"次后，账户将会冻结！");
            return result;
        }
        return Result.success(loginResp);
    };


    //通过用户名或者手机号模糊查询用户
    @PostMapping(value = "/queryUser",produces = "application/json;charset=UTF-8")
    public Result<QueryUserAndTotalResp> queryUserByNameAndPhone(@RequestBody @Validated QueryUserReq queryUserReq){
        log.info("controller pageNo and pageSize :"+ queryUserReq.getPageNo()+","+queryUserReq.getPageSize());
        QueryUserAndTotalResp queryUserAndTotalResp = userService.queryUserByNamePhone(queryUserReq);
        return Result.success(queryUserAndTotalResp);
    }


    //修改密码
    @PostMapping(value = "/updatePassword",produces = "application/json;charset=UTF-8")
    public Result<UpdatePassResp>  updatePasswordController(@RequestBody @Validated  UpdatePassReq updatePassReq){
        Result result = userService.updateUserPassword(updatePassReq);
        return result;

    }

    @GetMapping (value = "/queryUserDetail",produces = "application/json;charset=UTF-8")
    public Result<UsrInfo>  queryUserDetailController( Long id){
        UsrInfo usrInfo =  userService.queryUserDetail(id);
        return Result.success(usrInfo);

    }

    @PostMapping (value = "/updateUser",produces = "application/json;charset=UTF-8")
    public Result  updateUserSelfController(@RequestBody @Validated UpdateUserInfoReq updateUserInfoReq){
        boolean result = userService.updateUserinfoSelf(updateUserInfoReq);
        if(result){
            return Result.success();
        }
        return Result.failure();
    }



}
