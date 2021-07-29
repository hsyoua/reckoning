package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dto.req.UpdatePassReq;
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
     * @param user
     * @return
     */
    List<UsrInfo> queryUserByNamePhone(UsrInfo user);

    Integer queryCountByNamePhone(UsrInfo user);

    boolean updateUserPassword(UpdatePassReq updatePassReq);

    UsrInfo queryUserDetail(long id);


    boolean updateUserinfoSelf(UsrInfo usrInfo);

}
