package com.zzkk.interceptor;

import com.zzkk.utils.TokenResolve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author warmli
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("admin token验证...");
        /** 获取token */
        String token = request.getHeader("token");

        if(StringUtils.isEmpty(token) || StringUtils.isBlank(token))
            return false;

        /** 构造密钥和验证器 */
        String user = TokenResolve.getUser(token).getNumber();
        if(user == null || (!user.equals("root")))
            return false;

        System.out.println("admin token 验证成功...");
        return true;
    }
}
