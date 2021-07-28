package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.QueryUserReq;
import cn.yugutou.reckoning.dto.req.RegisterReq;

import java.util.List;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    UsrInfo login(LoginReq loginReq);

    List<UsrInfo>  queryUserByNamePhone(QueryUserReq queryUserReq );

}
