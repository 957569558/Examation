package com.zzkk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zzkk.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author warmli
 */
public class TokenResolve {
    public static User getUser(String token){
        if(token == null || StringUtils.isEmpty(token) || StringUtils.isBlank(token))
            return null;

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
        User u = new User();
        u.setNumber(user.asString());
        u.setPassword(pwd.asString());
        return u;
    }

    public static String generateToken(User user){
        Date nowDate = new Date();
        long time = System.currentTimeMillis();
        time += 30*1000*60;
        Date expireDate = new Date(time);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create().withHeader(map)
                /* 设置 载荷 Payload */
                .withClaim("user", user.getNumber()).withClaim("password", user.getPassword())
                .withIssuer("SERVICE")// 签名是有谁生成 例如 服务器
                .withSubject("user token")// 签名的主题
                .withAudience("APP")// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(nowDate) // 生成签名的时间
                .withExpiresAt(expireDate)// 签名过期的时间
                /* 签名 Signature */
                .sign(algorithm);

        return token;
    }
}
