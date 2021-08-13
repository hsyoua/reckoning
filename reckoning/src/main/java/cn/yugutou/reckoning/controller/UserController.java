package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.*;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.common.Result;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.SMSUtil;
import cn.yugutou.reckoning.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",produces="application/json;charset=UTF-8")
    public Result<RegisterResp> registerUser(@RequestBody @Validated RegisterReq requset,HttpSession session){
        boolean result = userService.registerUser(requset);
        //验证码注册
        String inputCode = requset.getCode();
        String mobileNo = requset.getMobileNo();
        String sessionCode = (String) session.getAttribute(mobileNo);
        if (!inputCode.equals(sessionCode)){
            throw new CustomException(ResultCode.USER_MESSAGE_LOGIN_CHECK);
        }
        RegisterResp resp = new RegisterResp(result);
        return Result.success(resp);
    }

    @PostMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public Result<LoginResp> login(@RequestBody @Validated LoginReq loginReq, HttpSession session){
        log.info("login request is [{}]",loginReq);
        LoginResp loginResp = userService.login(loginReq,session);
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
        if (!TokenUtils.checkUserId(id)){
           id = TokenUtils.getUserId();
        }
        return userService.queryUserDetail(id);

    }

    @PostMapping (value = "/updateUser",produces = "application/json;charset=UTF-8")
    public Result  updateUserSelfController(@RequestBody @Validated UpdateUserInfoReq updateUserInfoReq){
        boolean result = userService.updateUserinfoSelf(updateUserInfoReq);
        if(result){
            return Result.success();
        }
        return Result.failure();
    }

    /**
     * 获取登录验证码
     * @param req
     * @return
     */
    @PostMapping (value = "/getLoginMessage",produces = "application/json;charset=UTF-8")
    public Result  getLoginMessageController(@RequestBody @Validated SendMsgReq req,HttpSession session){
        //生成六位随机数
        Random random =new Random();
        String code =(random.nextInt(900000)+100000)+"";
       //获取手机号
        String mobileNo = req.getMobileNo();
        boolean send = SMSUtil.send(mobileNo, code);
        if(send){
            session.setAttribute(mobileNo,code);
            //设置
            session.setMaxInactiveInterval(60);
            return Result.success();
        }
        return Result.failure();
    }

    @PostMapping (value = "/loginByMessage",produces = "application/json;charset=UTF-8")
    public Result  msgLoginController(@RequestBody @Validated SendMsgReq req,HttpSession session){
        String mobileNo = req.getMobileNo();

        String inputCode = req.getCode();
        String sessionCode= (String) session.getAttribute(mobileNo);
        LoginResp loginResp = userService.queryUserinfoByPhone(mobileNo);

        //1.判断验证码是否正确

        if (!inputCode.equals(sessionCode)){
           throw new CustomException(ResultCode.USER_MESSAGE_LOGIN_CHECK);
        }
        //2.判断手机号是否存在
        if (loginResp == null){
            throw  new CustomException(ResultCode.USER_MESSAGE_LOGIN);
        }
        return Result.success(loginResp);

    }

}
