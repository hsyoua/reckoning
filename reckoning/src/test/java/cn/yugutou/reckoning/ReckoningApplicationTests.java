package cn.yugutou.reckoning;

import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReckoningApplicationTests {

        @Autowired
        private BillingMapper billingMapper;
    @Test
    void contextLoads() {
        //QueryBillDetailReq req = new QueryBillDetailReq();
        //req.setUserId(2);
        //long billId = 243759642601L;
        ////req.setBillingId(billId);
        //
        ////List<QueryBillDetailResp> billDetail = billingMapper.findBillDetail(req);
        //System.out.println(billDetail);


    }

}
