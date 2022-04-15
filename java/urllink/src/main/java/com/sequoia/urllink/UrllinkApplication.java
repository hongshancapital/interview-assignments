package com.sequoia.urllink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@ServletComponentScan
@MapperScan("com.sequoia.urllink.dao")
public class UrllinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrllinkApplication.class, args);
    }



}
