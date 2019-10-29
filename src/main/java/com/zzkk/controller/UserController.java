package com.zzkk.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zzkk.model.User;
import com.zzkk.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author warmli
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView login(){
        System.out.println("index...");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user){
        ModelAndView mv = new ModelAndView();
        User user1 = userService.getUser(user.getNumber());

        if(user1 == null || (!user1.getPassword().equals(user.getPassword()))){
            return "error";
        }

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
