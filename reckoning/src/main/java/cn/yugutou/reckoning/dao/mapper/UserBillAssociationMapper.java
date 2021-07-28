package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserBillAssociationMapper {
    /**
     * 增加和账单关联的用户信息
     * @param userBillAssociations
     * @return
     */
    boolean addUserBillAssociation(List<UserBillAssociation> userBillAssociations);
}
