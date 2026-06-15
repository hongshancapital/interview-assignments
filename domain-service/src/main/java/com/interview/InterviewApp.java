package com.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2021/12/17 10:57
 */

@SpringBootApplication
@EnableSwagger2
public class InterviewApp {

    public static void main(String[] args) {
        SpringApplication.run(InterviewApp.class, args);
    }
}
