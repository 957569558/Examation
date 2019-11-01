package com.zzkk.mapper;

import com.zzkk.model.Register;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterMapper {
    void cancelRegister(String uid, String eid);

    void deleteRegister(String ename);

    Boolean registerExam(Register register);
}
