package cn.yugutou.reckoning.service.impl;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.dao.entity.BillingInfo;
import cn.yugutou.reckoning.dao.entity.ReviewInfo;
import cn.yugutou.reckoning.dao.entity.UserBillAssociation;
import cn.yugutou.reckoning.dao.entity.UsrInfo;
import cn.yugutou.reckoning.dao.mapper.BillingMapper;
import cn.yugutou.reckoning.dao.mapper.ReviewMapper;
import cn.yugutou.reckoning.dao.mapper.UserBillAssociationMapper;
import cn.yugutou.reckoning.dao.mapper.UserMapper;
import cn.yugutou.reckoning.dto.req.BillReq;
import cn.yugutou.reckoning.dto.req.QueryBillDetailReq;
import cn.yugutou.reckoning.dto.req.QueryBillingInfoReq;
import cn.yugutou.reckoning.dto.req.UserBillAssociationReq;
import cn.yugutou.reckoning.dto.resp.QueryBillDetailResp;
import cn.yugutou.reckoning.dto.resp.QueryBillingInfoResp;
import cn.yugutou.reckoning.dto.resp.UserBillingInfoResp;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.interceptor.RequestContext;
import cn.yugutou.reckoning.service.BillService;
import cn.yugutou.reckoning.service.UserService;
import cn.yugutou.reckoning.utils.DateUtil;
import cn.yugutou.reckoning.utils.NumberGenerator;
import cn.yugutou.reckoning.utils.TokenUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("BillService")
@Slf4j
@Transactional
public class BillServiceImpl implements BillService {

    @Autowired
    private BillingMapper billingMapper;

    @Autowired
    private UserBillAssociationMapper userBillAssociationMapper;

    @Autowired
    private UserMapper userMapper;


    private final static String DEFAULT_PAYMENT_STATUS = "01";
    private final static String DEFAULT_BILLING_STATUS = "01";

    @Override
    public Long addBill(BillReq billReq) {
        BillingInfo billingInfo = new BillingInfo();
        BeanUtils.copyProperties(billReq, billingInfo);
        //????????????id
        Long billid = NumberGenerator.getNumber(12);



        billingInfo.setBillingId(billid);
        billingInfo.setBillingStatus(DEFAULT_BILLING_STATUS);
        billingMapper.addBill(billingInfo);
        return billid;
    }

    @Override
    public Long addUserBillAssociation(List<UserBillAssociationReq> userBillAssociationReqs, Long billid, BigDecimal amount) {
        ArrayList<UserBillAssociation> userBillAssociations = new ArrayList<>();
        //calculate apportionment amount
        /*????????????*/
        BigDecimal apportionedAmount = amount.divide(BigDecimal.valueOf(userBillAssociationReqs.size()));
        log.info("default allocation method is 'AA' ???apportioned amount is : {}", apportionedAmount);
        //check participating user information
        for (UserBillAssociationReq userBillAssociationReq : userBillAssociationReqs) {
            //???????????????id?????????????????????????????????
            Long userId = userBillAssociationReq.getUserId();
            if (userId == null || userBillAssociationReq.getUserParticipationType() == null) {
                throw new CustomException(ResultCode.BILL_PARAMETER_BE_EMPTY);
            }
            //?????????????????????id????????????????????????
            UsrInfo usrInfo = userMapper.queryUserDetail(userId);
            if (usrInfo == null) {
                return userId;
            }

            UserBillAssociation userBillAssociation = new UserBillAssociation();
            BeanUtils.copyProperties(userBillAssociationReq, userBillAssociation);
            long userbillid = NumberGenerator.getNumber(12);
            userBillAssociation.setAssociationId(userbillid);
            userBillAssociation.setApportionedAmount(apportionedAmount);
            userBillAssociation.setBillingId(billid);
            userBillAssociation.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
            userBillAssociations.add(userBillAssociation);
        }
        log.info("userBillAssociation final save list [{}]", userBillAssociations);
        userBillAssociationMapper.addUserBillAssociation(userBillAssociations);
        return null;
    }

    @Override
    public QueryBillDetailResp findBillDetail(QueryBillDetailReq req) {
        //TODO: ????????????????????????????????? ??????
        Long userId = req.getUserId();
        if (!TokenUtils.checkUserId(userId)){
            req.setUserId(TokenUtils.getUserId());
        }
        //??????????????????
        QueryBillDetailResp billDetails = billingMapper.findBillDetail(req);


        /*???????????????null?????????????????????????????????????????????*/
        if (billDetails == null) {
            throw new CustomException(ResultCode.FIND_BILL_DETAIL_ERROR);
        }
        ;

        return billDetails;
    }


    @Override
    public QueryBillingInfoResp queryUserBillingInfo(QueryBillingInfoReq queryBillingInfoReq) {
        Integer pageNo = queryBillingInfoReq.getPageNo();
        Integer pageSize = queryBillingInfoReq.getPageSize();
        Timestamp date_start = null;
        Timestamp  date_end =null;
        Date   dissipateTimeStart = queryBillingInfoReq.getDissipateTimeStart();
        Date  dissipateTimeEnd = queryBillingInfoReq.getDissipateTimeEnd();

        if (dissipateTimeStart !=null  ){
         date_start = DateUtil.getSqlTimestamp(dissipateTimeStart);
        }
        if (dissipateTimeEnd !=null){
         date_end = DateUtil.getSqlTimestamp(dissipateTimeEnd);
        }
        queryBillingInfoReq.setDissipateTimeStart(date_start);
        queryBillingInfoReq.setDissipateTimeEnd(date_end);

        // System.out.println("?????????"+queryBillingInfoReq.getDissipateTimeStart());
        if (pageSize > 30) {
            throw new CustomException(ResultCode.BILL_PAGESIZE_MAX);
        }
        Page page = PageHelper.startPage(pageNo, pageSize);
        log.info("queryUserBillingInfo?????????page??????:", page.getPageNum() + page.getPageSize());
        List<UserBillingInfoResp> userBillingInfoRespList =  billingMapper.queryUserBillingInfo(queryBillingInfoReq);
        QueryBillingInfoResp queryBillingInfoResp = new QueryBillingInfoResp();
        queryBillingInfoResp.setUserBillingInfo(userBillingInfoRespList);
        queryBillingInfoResp.setTotals(page.getTotal());
        return queryBillingInfoResp;
    }


}
