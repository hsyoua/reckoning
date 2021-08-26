package cn.yugutou.reckoning.utils;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import cn.yugutou.reckoning.interceptor.RequestContext;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class TokenUtils {

    public static Long getUserId() {
        Long userId =null;
        if (!StringUtils.isEmpty(JWTUtil.getUserId(RequestContext.get()))){

         userId = Long.valueOf(JWTUtil.getUserId(RequestContext.get()));
        }

        if (userId != null) {
            return userId;
        } else {
            throw new CustomException(ResultCode.TOKEN_GET_USERID);
        }
    }


    public static boolean checkUserId(Long id) {
        Long userId = getUserId();
        if (id != userId) {
            return false;
        }
        return true;

    }
    222222

}
