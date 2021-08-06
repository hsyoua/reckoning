package cn.yugutou.reckoning.interceptor;

import cn.yugutou.reckoning.common.JWTUtil;
import cn.yugutou.reckoning.exception.CustomException;
import cn.yugutou.reckoning.exception.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtIntercepter implements HandlerInterceptor {
    //主要功能拦截请求验证token
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CustomException(ResultCode.TOKEN_CANNOT_BE_EMPTY);
        }
        if (!JWTUtil.verify(token)) {
            throw new CustomException(ResultCode.TOKEN_VERIFICATION_FAILED);
        }
        RequestContext.set(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContext.remove();
    }
}

