package com.ikaileblog;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ikaileblog.dao.mapper")
public class IkaileBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(IkaileBlogApplication.class, args);
    }

}
