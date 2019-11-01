package com.zzkk.service;

import com.zzkk.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author warmli
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    public String getMessage(){
        return messageMapper.getMessage();
    }
}
