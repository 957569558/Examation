package com.zzkk.interceptor;

import com.zzkk.utils.TokenResolve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author warmli
 */
public class WebAppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("token验证...");
        /** 获取token */
        String token = request.getHeader("token");
        if(token == null || StringUtils.isEmpty(token) || StringUtils.isBlank(token))
            return false;

        /** 构造密钥和验证器 */
        String user = TokenResolve.getUser(token).getNumber();
        System.out.println(user);
        if(user.equals("root"))
            return false;
        return true;
    }
}
