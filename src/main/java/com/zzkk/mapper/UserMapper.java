package com.zzkk.mapper;

import com.zzkk.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author warmli
 */
@Repository
public interface UserMapper {
    User getUser(String number);

    void addUser(User user);

    Integer countByNumber(String number);

    Boolean updateUserByNumber(String number);

    List<User> getRegisterUser(String ename);
}
