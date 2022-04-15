package com.example.shorturlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
//@EnableOpenApi
//@EnableSwagger2
public class ShorturlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlServiceApplication.class, args);
    }

}
