package com.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ：ji
 * @description：Springboot启动类
 */
@EnableScheduling
@SpringBootApplication
public class DomainApplication {

    public static void main(String[] args){
        SpringApplication.run(DomainApplication.class,args);
    }
}
