package com.zzkk.service;

import com.zzkk.mapper.UserMapper;
import com.zzkk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author warmli
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUser(String number){
        return userMapper.getUser(number);
    }
}
