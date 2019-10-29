package com.zzkk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zzkk.mapper")
@SpringBootApplication
public class ExamationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamationApplication.class, args);
    }

}
