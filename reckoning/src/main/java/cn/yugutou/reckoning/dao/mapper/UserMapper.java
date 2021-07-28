package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
