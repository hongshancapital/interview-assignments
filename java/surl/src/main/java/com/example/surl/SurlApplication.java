package com.example.surl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.example.surl.mapper")
public class SurlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurlApplication.class, args);
        System.out.println("-----------------启动成功-----------------");
    }

}
