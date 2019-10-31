package com.zzkk.controller;

import com.zzkk.model.Examination;
import com.zzkk.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author warmli
 */
@Controller
public class ExamController {
    @Autowired
    ExamService examService;

    @GetMapping("/allExam")
    public ModelAndView allExam(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exam", examService.allExam());
        mv.setViewName("index");
        return mv;
    }

    @PostMapping("/addExam")
    @ResponseBody
    public String addExam(@RequestBody Examination exam){
        System.out.println(exam);
        String eid = UUID.randomUUID().toString().replaceAll("-","");
        exam.setEid(eid);
        exam.setDateTime(exam.getEdate()+" "+exam.getEtime());
        examService.addExam(exam);
        return "true";
    }

    @DeleteMapping("/deleteExam")
    @ResponseBody
    public String deleteExam(@RequestBody Examination exam){
        System.out.println(exam);
        examService.deleteExam(exam.getEname());
        return "";
    }
}
