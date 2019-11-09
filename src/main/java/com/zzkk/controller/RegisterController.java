package com.zzkk.controller;

import com.zzkk.model.Examination;
import com.zzkk.model.User;
import com.zzkk.service.ExamService;
import com.zzkk.service.MessageService;
import com.zzkk.service.RegisterService;
import com.zzkk.service.UserService;
import com.zzkk.utils.TokenResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @Autowired
    RegisterService registerService;

    @GetMapping("/registerOp")
    public String registerOp(@RequestParam(value = "token") String token){
        User user = TokenResolve.getUser(token);
        if(user == null)
            return "login";
        return "registerOp";
    }

    @GetMapping("/exportRegister")
    @ResponseBody
    public String exportRegister(HttpServletResponse response, @RequestParam(value = "token") String token,
                                 @RequestParam(value = "ename") String ename){
        User user = TokenResolve.getUser(token);
        if(user == null)
            return "";

        return registerService.downloadFile(response, ename);
    }

    @PostMapping("/registerExam")
    @ResponseBody
    public String registerExam(@RequestBody Examination exam, @RequestHeader(value = "token") String token){
        System.out.println(exam.getEname() == null);
        User user = TokenResolve.getUser(token);

        if(examService.registerExam(exam, user.getNumber()))
            return "报名成功";
        else
        return "报名失败";
    }

    @PostMapping("/cancelExam")
    @ResponseBody
    public String cancelExam(@RequestBody Examination exam, @RequestHeader(value = "token") String token){
        System.out.println(exam.getEname() == null);
        User user = TokenResolve.getUser(token);

        if(examService.cancelRegister(exam, user.getNumber()))
            return "取消成功";
        else
            return "取消失败";
    }

    @GetMapping("/register")
    public ModelAndView register(@RequestParam(value = "token") String token){
        System.out.println("register...");
        ModelAndView mv = new ModelAndView();
        User user = TokenResolve.getUser(token);
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
}
