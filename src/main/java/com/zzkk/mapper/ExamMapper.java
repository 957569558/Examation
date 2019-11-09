package com.zzkk.mapper;

import com.zzkk.model.Examination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamMapper {
    Examination getExamByName(String ename);

    Boolean addExam(Examination exam);

    void deleteExam(String ename);

    List<Examination> allExam();

    List<Examination> getRegExam(String dateTime);

    List<Examination> getRegedExam(String user);
}
