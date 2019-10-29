package com.zzkk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author warmli
 */
@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
