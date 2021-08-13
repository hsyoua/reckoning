package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.common.Result;
import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.*;
import cn.yugutou.reckoning.dto.resp.LoginResp;
import cn.yugutou.reckoning.dto.resp.QueryUserAndTotalResp;
import cn.yugutou.reckoning.dto.resp.QueryUserResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.Md5AddSaltTool;
import cn.yugutou.reckoning.utils.NumberGenerator;
import cn.yugutou.reckoning.utils.RSAUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

        String password = requset.getPassword();
        String encryptedPwd = null;
        try {
            //生成秘钥密码
            encryptedPwd = Md5AddSaltTool.getEncryptedPwd(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requset.setPassword(encryptedPwd);

        BeanUtils.copyProperties(requset, usrInfo);
        Long userId = NumberGenerator.getNumber(9);
        System.out.println(userId);
        usrInfo.setUserId(userId);
        usrInfo.setUserStatus("01");
        usrInfo.setPasswordErrorNum(0);
        //只能创建普通用户
        usrInfo.setUserRole("02");
        UsrInfo queryUsrInfo = userMapper.queryUsrInfoByPhone(requset.getMobileNo());
        if (queryUsrInfo != null) {
            logger.info("user phone already exists");
            throw new CustomException(ResultCode.USER_ALREAD_EXISTS);
        }
        return userMapper.saveUser(usrInfo);
    }

    @Override
    public LoginResp login(LoginReq loginReq, HttpSession session) {
        String mobileNo = loginReq.getMobileNo();
        //公钥加密后的密码
        String password = loginReq.getPassword();
        //query userInfo by mobileNo
        log.debug("login user moblie no [{}]", loginReq.getMobileNo());
        UsrInfo usrInfo = userMapper.queryUsrInfoByPhone(mobileNo);
        if (usrInfo == null) {
            throw new CustomException(ResultCode.USER_NOT_EXISTS);
        }
        // TODO :加密校验start
        //获取私钥
        String privateKey = (String) session.getAttribute(mobileNo);
        log.info("【登录获取的私钥：】{}", privateKey);
        //私钥解密
        String privatePass = null;
        boolean flag =false;
        try {
            privatePass = RSAUtils.decryptByPrivateKey(privateKey, password);
            //解密后通过通过加盐与数据库进行校验
             flag = Md5AddSaltTool.validPassword(privatePass, usrInfo.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO :加密校验end
         //关闭校验，开关设为1
         int   switchKey = 1;

        boolean judge ;
        if (switchKey ==1){
            judge =flag;
        }else {
            judge = StringUtils.equals(loginReq.getPassword(), usrInfo.getPassword());
        }

        //check user status
        if (!"01".equals(usrInfo.getUserStatus())) {
            throw new CustomException(ResultCode.USER_STATUS_EXCEPTION);
        }
        LoginResp loginResp = new LoginResp();
        //check user login password
        //TODO:加密校验
        //if (!StringUtils.equals(loginReq.getPassword(), usrInfo.getPassword())) {
        if (!judge) {
            int errorNum = usrInfo.getPasswordErrorNum();
            if (++errorNum >= 5) {
                //update userStatus frozen
                userMapper.frozenStatusById(usrInfo.getUserId(), errorNum);
                loginResp.setErrorNum(0);
            } else {
                //update errorNum
                userMapper.updateErrorNumById(usrInfo.getUserId(), errorNum);
                //Error five times, the account will be frozen
                Integer remainingTimes = 5 - errorNum;
                loginResp.setErrorNum(remainingTimes);
            }
            return loginResp;
        }
        //update last login time;
        userMapper.updateLoginTime(usrInfo.getUserId());
        BeanUtils.copyProperties(usrInfo, loginResp);
        //set token
        loginResp.setToken(JWTUtil.jwtCreate(String.valueOf(usrInfo.getUserId())));
        log.info("user login success!");
        return loginResp;

    }

    @Override
    public QueryUserAndTotalResp queryUserByNamePhone(QueryUserReq queryUserReq) {
        Integer pageSize = queryUserReq.getPageSize();
        Integer pageNo = queryUserReq.getPageNo();
        if (pageSize > 50) {
            throw new CustomException(ResultCode.user_pagesize_max);
        }

        Page page = PageHelper.startPage(pageNo, pageSize);
        log.info("page:[{}]", page.getPageNum() + page.getPageSize());
        List<QueryUserResp> queryUserResps = userMapper.queryUserByNamePhone(queryUserReq);
        QueryUserAndTotalResp queryUserAndTotalResp = new QueryUserAndTotalResp();
        queryUserAndTotalResp.setTotalNum(page.getTotal());
        queryUserAndTotalResp.setUserInfoList(queryUserResps);
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
        String inputNewPass = updatePassReq.getNewPass();
        String confirmNesPass = updatePassReq.getConfirmNesPass();
        if (!oldPass.equals(inoutOldPass)) {
            return Result.failure(ResultCode.USER_OLDPASSWORD_ERROR);
        }
        ;
        if (inoutOldPass.equals(inputNewPass)) {
            return Result.failure(ResultCode.USER_NEWPASSWORD_ERROR);
        }
        ;
        if (!confirmNesPass.equals(inputNewPass)) {
            return Result.failure(ResultCode.USER_CONFIRMPASSWORD_ERROR);
        }
        ;

        if (userMapper.updateUserPassword(updatePassReq)) {
            return Result.failure(ResultCode.SUCCESS);
        }


        return null;
    }

    @Override
    public Result queryUserDetail(Long id) {
        log.info("user id [{}]", id);
        UsrInfo usrInfo = userMapper.queryUserDetail(id);
        Result result = null;
        if (usrInfo == null) {
            result = Result.failure();
        } else {
            result = Result.success(usrInfo);
        }
        return result;
    }

    @Override
    public boolean updateUserinfoSelf(UpdateUserInfoReq updateUserInfoReq) {
        UsrInfo usrInfo = new UsrInfo();
        BeanUtils.copyProperties(updateUserInfoReq, usrInfo);

        return userMapper.updateUserinfoSelf(usrInfo);
    }
}
