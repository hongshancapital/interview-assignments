package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: shican.sc
 * @Date: 2022/6/12 21:35
 * @see
 */
@SpringBootApplication
public class BootStrap {

    public static void main(String[] args){
        SpringApplication.run(BootStrap.class, args);
        System.out.println("BootStrap server start!");
    }


}
