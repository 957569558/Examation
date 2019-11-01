package com.zzkk.service;

import com.zzkk.mapper.ExamMapper;
import com.zzkk.mapper.RegisterMapper;
import com.zzkk.mapper.UserMapper;
import com.zzkk.model.Examination;
import com.zzkk.model.Register;
import com.zzkk.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author warmli
 */
@Service
public class ExamService {
    @Autowired
    ExamMapper examMapper;
    @Autowired
    RegisterMapper registerMapper;
    @Autowired
    UserMapper userMapper;

    public Boolean addExam(Examination exam){
        return examMapper.addExam(exam);
    }

    @Transactional
    public boolean cancelRegister(Examination exam, String number){
        User user = userMapper.getUser(number);
        Examination e = examMapper.getExamByNumber(exam.getEname());
        if(e == null || StringUtils.isEmpty(e.getEid()) || StringUtils.isBlank(e.getEid()))
            return false;

        if(StringUtils.isEmpty(user.getUid()) || StringUtils.isBlank(user.getUid()))
            return false;


        registerMapper.cancelRegister(user.getUid(), e.getEid());
        return true;
    }

    @Transactional
    public boolean registerExam(Examination exam, String number){
        User user = userMapper.getUser(number);
        Examination e = examMapper.getExamByNumber(exam.getEname());
        if(e == null || StringUtils.isEmpty(e.getEid()) || StringUtils.isBlank(e.getEid()))
            return false;

        if(StringUtils.isEmpty(user.getUid()) || StringUtils.isBlank(user.getUid()))
            return false;

        Register register = new Register(UUID.randomUUID().toString().replaceAll("-",""), user.getUid(), e.getEid());
        registerMapper.registerExam(register);
        return true;
    }

    @Transactional
    public void deleteExam(String ename){
        registerMapper.deleteRegister(ename);
        examMapper.deleteExam(ename);
    }

    public List<Examination> allExam() {
        return examMapper.allExam();
    }

    public List<Examination> getRegExam(String dateTime){
        return examMapper.getRegExam(dateTime);
    }

    public List<Examination> getRegedExam(String user){
        return examMapper.getRegedExam(user);
    }
}
