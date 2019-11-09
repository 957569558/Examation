package com.zzkk.controller;

import com.zzkk.model.User;
import com.zzkk.service.UserService;

import com.zzkk.utils.TokenResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author warmli
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/userOp")
    public String UserOp(@RequestParam(value = "token") String token){
        User user = TokenResolve.getUser(token);
        if(user == null)
            return "login";
        return "userOp";
    }

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
        User user1 = userService.getUser(user.getNumber());

        if(user1 == null || (!user1.getPassword().equals(user.getPassword()))){
            return "error";
        }

        return TokenResolve.generateToken(user);
    }

    @PostMapping("/importUser")
    public String importUser(@RequestParam(value="upload_file",required=true) MultipartFile upload_file, Model model){
        String fileName = upload_file.getOriginalFilename();
        try {
            userService.importUser(fileName, upload_file);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "解析文件失败");
        } finally {
            return "userOp";
        }
    }
}
