package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.QueryUserReq;
import cn.yugutou.reckoning.dto.req.RegisterReq;
import cn.yugutou.reckoning.dto.req.UpdatePassReq;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.exception.Result;

import java.util.List;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    LoginResp login(LoginReq loginReq);

    List  queryUserByNamePhone(QueryUserReq queryUserReq );


    Result updateUserPassword(UpdatePassReq updatePassReq);

}
