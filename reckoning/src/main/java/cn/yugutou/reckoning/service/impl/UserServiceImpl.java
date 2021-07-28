package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.LoginReq;
import cn.yugutou.reckoning.dto.req.QueryUserReq;
import cn.yugutou.reckoning.dto.req.RegisterReq;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.CheckUtil;
import cn.yugutou.reckoning.utils.NumberGenerator;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("UserService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public boolean registerUser(RegisterReq requset) {
        UsrInfo usrInfo = new UsrInfo();
        BeanUtils.copyProperties(requset,usrInfo);
        Long userId = NumberGenerator.getNumber(9);
        System.out.println(userId);
        usrInfo.setUserId(userId);
        usrInfo.setUserStatus("01");
        usrInfo.setPasswordErrorNum(0);
        //只能创建普通用户
        usrInfo.setUserRole("02");
        UsrInfo queryUsrInfo = userMapper.queryUsrInfoByPhone(requset.getMobileNo());
        if(queryUsrInfo!=null){
            logger.info("user phone already exists");
            throw new CustomException(ResultCode.USER_ALREAD_EXISTS);
        }
        return userMapper.saveUser(usrInfo);
    }

    @Override
    public LoginResp login(LoginReq loginReq) {
        //query userInfo by mobileNo
        log.debug("login user moblie no [{}]",loginReq.getMobileNo());
        UsrInfo usrInfo =  userMapper.queryUsrInfoByPhone(loginReq.getMobileNo());
        //check user status
        if(!"01".equals(usrInfo.getUserStatus())) {
            throw new CustomException(ResultCode.USER_STATUS_EXCEPTION);
        }
        //check user login password
        if(!StringUtils.equals(loginReq.getPassword(),usrInfo.getPassword())){
            Integer errorNum = usrInfo.getPasswordErrorNum();
            if(++errorNum>=5){
                //update userStatus frozen
                userMapper.frozenStatusById(usrInfo.getUserId(),errorNum);
            }else {
                //update errorNum
                userMapper.updateErrorNumById(usrInfo.getUserId(),errorNum);
            }
            throw new CustomException(ResultCode.USER_LOGIN_CHECK_FAIL);
        }
        //update last login time;
        userMapper.updateLoginTime(usrInfo.getUserId());
        log.info("user login success!");
        LoginResp loginResp = new LoginResp();
        BeanUtils.copyProperties(usrInfo,loginResp);
        return loginResp;

    }

    @Override
    public List<UsrInfo> queryUserByNamePhone(QueryUserReq queryUserReq) {
        UsrInfo usrInfo = new UsrInfo();
        String keyWord = queryUserReq.getKeyWord();
        BeanUtils.copyProperties(queryUserReq,usrInfo);
         /*判断参数是否是数字*/
        boolean numeric = CheckUtil.isNumeric(keyWord);
        if(numeric){
            usrInfo.setMobileNo(keyWord);
        };

            usrInfo.setUserName(keyWord);

        log.info("pageNo,pageSize"+usrInfo.getPageNo()+","+usrInfo.getPageSize());
        List<UsrInfo> usrInfos = userMapper.queryUserByNamePhone(usrInfo);
        return usrInfos;
    }
}
