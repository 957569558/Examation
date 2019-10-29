package com.zzkk.mapper;

import com.zzkk.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author warmli
 */
@Repository
public interface UserMapper {
    User getUser(String number);
}
