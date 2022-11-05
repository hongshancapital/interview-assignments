package com.example.shorturlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Description
 * @Author xingxing.yu
 * @Date 2022/04/15 16:55
 **/
@SpringBootApplication
@EnableWebMvc
public class ShorturlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlServiceApplication.class, args);
    }

}
