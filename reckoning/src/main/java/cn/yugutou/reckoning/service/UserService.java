package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.RegisterReq;
import cn.yugutou.reckoning.dto.resp.LoginResp;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    LoginResp login(LoginReq loginReq);
}
