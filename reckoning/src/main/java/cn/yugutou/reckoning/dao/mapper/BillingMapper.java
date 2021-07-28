package cn.yugutou.reckoning.dao.mapper;

import cn.yugutou.reckoning.dao.entity.BillingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface BillingMapper {
    /**
     * 增加账单信息
     * @param billingInfo
     * @return
     */
    boolean addBill(BillingInfo billingInfo);

}
