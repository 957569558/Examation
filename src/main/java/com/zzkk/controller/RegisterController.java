package com.zzkk.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zzkk.model.Examination;
import com.zzkk.model.User;
import com.zzkk.service.ExamService;
import com.zzkk.service.MessageService;
import com.zzkk.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author warmli
 */
@Controller
public class RegisterController {
    @Autowired
    ExamService examService;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @PostMapping("/registerExam")
    @ResponseBody
    public String registerExam(@RequestBody Examination exam, @RequestHeader(value = "token") String token, Model model){
        System.out.println(exam.getEname() == null);
        User user = getUser(token);
        boolean success = examService.registerExam(exam, user.getNumber());
        if(success)
            return "报名成功";
        else
        return "报名失败";
    }

    @PostMapping("/cancelExam")
    @ResponseBody
    public String cancelExam(@RequestBody Examination exam, @RequestHeader(value = "token") String token, Model model){
        System.out.println(exam.getEname() == null);
        User user = getUser(token);
        boolean success = examService.cancelRegister(exam, user.getNumber());
        if(success)
            return "取消成功";
        else
            return "取消失败";
    }

    @GetMapping("/register")
    public ModelAndView register(@RequestParam(value = "token") String token){
        System.out.println("register...");
        ModelAndView mv = new ModelAndView();
        User user = getUser(token);
        if(user == null){
            mv.setViewName("login");
            return mv;
        }
        mv.setViewName("register");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(user.getNumber());
        List<Examination> reg = examService.getRegExam(sdf.format(new Date()));
        List<Examination> reged = examService.getRegedExam(user.getNumber());
        reged.stream().
                forEach(e -> {
                    for(int i = 0; i < reg.size(); i++){
                        if(e.getEname().equals(reg.get(i).getEname())){
                            reg.remove(i);
                        }
                    }
                });
        System.out.println(reg);
        System.out.println(reged);
        mv.addObject("reg", reg);
        mv.addObject("reged", reged);
        mv.addObject("msg", messageService.getMessage());
        User u = userService.getUser(user.getNumber());
        mv.addObject("user", u);
        return mv;
    }

    private User getUser(String token){
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
}
