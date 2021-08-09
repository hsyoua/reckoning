package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryUserAndTotalResp;
import cn.yugutou.reckoning.common.Result;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    LoginResp login(LoginReq loginReq);

    QueryUserAndTotalResp queryUserByNamePhone(QueryUserReq queryUserReq );


    Result updateUserPassword(UpdatePassReq updatePassReq);

    Result queryUserDetail(Long id) ;

    boolean updateUserinfoSelf(UpdateUserInfoReq updateUserInfoReq);

}
