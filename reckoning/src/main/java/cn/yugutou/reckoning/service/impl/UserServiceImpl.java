package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryUserAndTotalResp;
import cn.yugutou.reckoning.dto.resp.QueryUserResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.Result;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.NumberGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        log.debug("userinfo[{}]",usrInfo.getUserStatus());
        //check user status
        if(!("01".equals(usrInfo.getUserStatus()))) {
            throw new CustomException(ResultCode.USER_STATUS_EXCEPTION);
        }
        //check user login password
        if(!StringUtils.equals(loginReq.getPassword(),usrInfo.getPassword())){
            int errorNum = usrInfo.getPasswordErrorNum();
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
    public QueryUserAndTotalResp queryUserByNamePhone(QueryUserReq queryUserReq) {
        Integer pageSize = queryUserReq.getPageSize();
        Integer pageNo = queryUserReq.getPageNo();
        if (pageSize>50){
            throw new CustomException(ResultCode.user_pagesize_max);
        }

          Page page =PageHelper.startPage(pageNo,pageSize);
            log.info("page:[{}]",page.getPageNum()+ page.getPageSize());
            List<QueryUserResp> queryUserResps = userMapper.queryUserByNamePhone(queryUserReq);
        QueryUserAndTotalResp queryUserAndTotalResp =   new QueryUserAndTotalResp();
        queryUserAndTotalResp.setTotalNum(page.getTotal());
        queryUserAndTotalResp.setUserInfoByNamePhone(queryUserResps);
            return queryUserAndTotalResp;
    }

    @Override
    public Result updateUserPassword(UpdatePassReq updatePassReq) {
        Long userId = updatePassReq.getUserId();
        UsrInfo usrInfo = userMapper.queryUserDetail(userId);
        //获取旧密码
        String oldPass = usrInfo.getPassword();
        /*判断输入的旧密码是否正确,旧密码和新密码是否相同，确认密码是否与新密码相同*/
        String inoutOldPass = updatePassReq.getOldPass();
        String inputNewPass=updatePassReq.getNewPass();
        String confirmNesPass = updatePassReq.getConfirmNesPass();
        if (!oldPass.equals(inoutOldPass)){
            return Result.failure(ResultCode.USER_OLDPASSWORD_ERROR);
        };
        if (inoutOldPass.equals(inputNewPass)){
            return Result.failure(ResultCode.USER_NEWPASSWORD_ERROR);
        };
        if (!confirmNesPass.equals(inputNewPass)){
            return Result.failure(ResultCode.USER_CONFIRMPASSWORD_ERROR);
        };

        if (userMapper.updateUserPassword(updatePassReq)){
            return Result.failure(ResultCode.SUCCESS);
        }


        return null;
    }

    @Override
    public UsrInfo queryUserDetail(Long id) {
        log.info("user id [{}]",id);
        return userMapper.queryUserDetail(id);
    }

    @Override
    public boolean updateUserinfoSelf(UpdateUserInfoReq updateUserInfoReq) {
        UsrInfo usrInfo = new UsrInfo();
        BeanUtils.copyProperties(updateUserInfoReq,usrInfo);

        return userMapper.updateUserinfoSelf(usrInfo);
    }
}
