package cn.yugutou.reckoning.controller;

import cn.yugutou.reckoning.common.Result;
import cn.yugutou.reckoning.dto.req.FindReviewListByIdReq;
import cn.yugutou.reckoning.dto.resp.ReviewWaitingResp;
import cn.yugutou.reckoning.utils.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author chenqun
 * @Date 2021/8/10 18:09
 * @Description TODO
 */

@RestController
@Slf4j
@RequestMapping("/key")
public class RSAKeyController {


    @PostMapping(value = "/getPublicKey", produces = "application/json;charset=UTF-8")
    public Result getPublicKey(HttpServletRequest request) throws NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        //获取秘钥对
        RSAUtils.RSAKeyPair rsaKeyPair = RSAUtils.generateKeyPair();
        //私钥，存session
        String privateKey = rsaKeyPair.getPrivateKey();
        if(session.getAttribute("privateKeySu")!=null) {
            session.removeAttribute("privateKeySu");
        }
            session.setAttribute("privateKeySu",privateKey);
        //获取公钥，返回
        String publicKey = rsaKeyPair.getPublicKey();
         return Result.success(publicKey);

    }



}
