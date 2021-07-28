package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
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
    void updateLoginTime(Long userId);


    List<UsrInfo> queryUserByNamePhone(UsrInfo user);
}
