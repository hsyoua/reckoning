package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.*;
import cn.yugutou.reckoning.exception.Result;
import cn.yugutou.reckoning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",produces="application/json;charset=UTF-8")
    public ResponseEntity<RegisterResp> registerUser(@RequestBody @Validated RegisterReq requset){
        boolean result = userService.registerUser(requset);
        RegisterResp resp = new RegisterResp();
        resp.setResult(result);
        if(!result){
            return new ResponseEntity<>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @PostMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public ResponseEntity<LoginResp> login(@RequestBody @Validated LoginReq loginReq){
        log.info("login request is [{}]",loginReq);
        LoginResp loginResp = userService.login(loginReq);
        return new ResponseEntity<>(loginResp,HttpStatus.OK);
    };


    //通过用户名或者手机号模糊查询用户
    @PostMapping(value = "/queryUser",produces = "application/json;charset=UTF-8")
    public ResponseEntity<QueryUserResp> queryUserByNameAndPhone(@RequestBody @Validated QueryUserReq queryUserReq){
        log.info("controller pageNo and pageSize :"+ queryUserReq.getPageNo()+","+queryUserReq.getPageSize());
        //获取输入的起始页数，做转换后再封装
        Integer pageNo = queryUserReq.getPageNo();
        Integer pageSize = queryUserReq.getPageSize();


        pageNo=(pageNo-1)*pageSize;
        queryUserReq.setPageNo(pageNo);
        List   newUsrInfos = userService.queryUserByNamePhone(queryUserReq);
        ArrayList<QueryUserResp> queryUserResps = new ArrayList<>();
        List<UsrInfo> UsrInfos = (List<UsrInfo>) newUsrInfos.get(0);
        QueryUserAndTotalResp queryUserAndTotalResp = new QueryUserAndTotalResp();
        for (UsrInfo usrInfo : UsrInfos) {
            QueryUserResp queryUserResp = new QueryUserResp();
            BeanUtils.copyProperties(usrInfo,queryUserResp);
            queryUserResps.add(queryUserResp);
        }
        queryUserAndTotalResp.setQueryUserResps(queryUserResps);
        queryUserAndTotalResp.setTotalNum((Integer) newUsrInfos.get(1));

        return new ResponseEntity(queryUserAndTotalResp,HttpStatus.OK);
    }


    //修改密码
    @PostMapping(value = "/updatePassword",produces = "application/json;charset=UTF-8")
    public ResponseEntity<UpdatePassResp>  updatePasswordController(@RequestBody @Validated  UpdatePassReq updatePassReq){
        Result result = userService.updateUserPassword(updatePassReq);

        return new ResponseEntity(result,HttpStatus.OK);

    }

    @GetMapping (value = "/queryUserDetail",produces = "application/json;charset=UTF-8")
    public ResponseEntity  queryUserDetailController(  Long id){
        UsrInfo usrInfo =  userService.queryUserDetail(id);

        return new ResponseEntity(usrInfo,HttpStatus.OK);

    }

    @PostMapping (value = "/updateUser",produces = "application/json;charset=UTF-8")
    public ResponseEntity  updateUserSelfController(@RequestBody @Validated UpdateUserInfoReq updateUserInfoReq){
        boolean result = userService.updateUserinfoSelf(updateUserInfoReq);

        return new ResponseEntity(result,HttpStatus.OK);

    }

}
