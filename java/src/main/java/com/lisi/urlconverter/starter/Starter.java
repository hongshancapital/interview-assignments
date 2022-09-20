package com.lisi.urlconverter.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 启动类
 * @author: li si
 */
@SpringBootApplication
@ComponentScan("com.lisi.urlconverter")
public class Starter {
    public static void main(String[] args){
        SpringApplication.run(Starter.class, args);
    }
}
