package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryUserAndTotalResp;
import cn.yugutou.reckoning.dto.resp.QueryUserResp;
import cn.yugutou.reckoning.exception.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    LoginResp login(LoginReq loginReq);

    PageInfo<QueryUserResp> queryUserByNamePhone(QueryUserReq queryUserReq );


    Result updateUserPassword(UpdatePassReq updatePassReq);

    UsrInfo queryUserDetail(long id) ;

    boolean updateUserinfoSelf(UpdateUserInfoReq updateUserInfoReq);

}
