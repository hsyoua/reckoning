package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UsrInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    boolean saveUser(UsrInfo usrInfo);
    UsrInfo queryUsrInfoByPhone(@Param("mobileNo") String mobileNo);
}
