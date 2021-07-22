package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.RegisterReq;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.RegisterResp;
import cn.yugutou.reckoning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Validated
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
    public ResponseEntity<LoginResp> login(@RequestBody LoginReq loginReq){


        LoginResp loginResp = new LoginResp();
        return new ResponseEntity<>(loginResp,HttpStatus.OK);
    };
}
