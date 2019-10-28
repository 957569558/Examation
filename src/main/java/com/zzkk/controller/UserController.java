package com.zzkk.controller;

import com.zzkk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mojiayi
 */
@Controller
public class UserController {
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestBody User user){
        System.out.println("begin");
        System.out.println(user.getUser());
        System.out.println(user.getPassword());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
