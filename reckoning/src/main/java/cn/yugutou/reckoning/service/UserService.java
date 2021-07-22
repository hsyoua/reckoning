package cn.yugutou.reckoning.service;


import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.RegisterReq;

public interface UserService {
    boolean registerUser(RegisterReq registerRequset);

    UsrInfo login(RegisterReq loginReq);
}
