package com.zzkk.controller;

import com.zzkk.model.Examination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author warmli
 */
@Controller
public class ExamController {

    private List<Examination> generate(){
        List<Examination> list = new ArrayList<>();
        Examination e1 = new Examination();
        Examination e2 = new Examination();
        e1.setEid("ssss");
        e1.setEname("2-2");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hehe = dateFormat.format(now);
        e1.setEdate(hehe);
        e1.setRegister(1);
        e2.setEid("aaaa");
        e2.setEname("3-2");
        e2.setEdate(hehe);
        e2.setRegister(1);
        list.add(e1);
        list.add(e2);
        list.add(e1);
        list.add(e1);
        list.add(e2);
        list.add(e1);
        return list;
    }

    @GetMapping("/allExam")
    public ModelAndView allExam(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exam", generate());
        mv.setViewName("index");
        return mv;
    }

    @PostMapping("/addExam")
    public boolean addExam(){
        return true;
    }

}
