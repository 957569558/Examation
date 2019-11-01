package com.zzkk.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("SERVICE")
                .build();

        /** 根据验证器解析token获取信息 */
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        Claim user = claims.get("user");
        Claim pwd = claims.get("password");
        if(user == null || (user != null && !user.asString().equals("root")))
            return false;

        return true;
    }
}
