package com.wjup.shorturl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@MapperScan("com.wjup.shorturl.mapper") //扫描的mapper
@SpringBootApplication
@EnableSwagger2
public class ShorturlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlApplication.class, args);
    }

}
