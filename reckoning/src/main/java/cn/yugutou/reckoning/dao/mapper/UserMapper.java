package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.QueryUserReq;
import cn.yugutou.reckoning.dto.req.UpdatePassReq;
import cn.yugutou.reckoning.dto.resp.QueryUserResp;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     * @param usrInfo
     * @return
     */
    boolean saveUser(UsrInfo usrInfo);
    UsrInfo queryUsrInfoByPhone(@Param("mobileNo") String mobileNo);
    void frozenStatusById(@Param("userId") Long userId,@Param("errorNum") Integer errorNum);
    void updateErrorNumById(@Param("userId") Long userId,@Param("errorNum") Integer errorNum);
    void updateLoginTime(Long userId);

    /**
     * 通过手机号或者用户名查询用户
     * @param
     * @return
     */
    List<QueryUserResp> queryUserByNamePhone(QueryUserReq queryUserReq);


    boolean updateUserPassword(UpdatePassReq updatePassReq);

    UsrInfo queryUserDetail(@Param("id") Long id);


    boolean updateUserinfoSelf(UsrInfo usrInfo);

}
