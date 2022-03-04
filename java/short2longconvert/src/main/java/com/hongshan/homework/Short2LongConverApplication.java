package com.hongshan.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Short2LongConverApplication {
    public static void main(String[] args) {
        SpringApplication.run(Short2LongConverApplication.class,args);
    }
}
