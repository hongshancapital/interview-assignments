package com.demo.urlshortlink;
/*
 启动类
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class urlshortApplication {

    public static void main(String[] args) {
        SpringApplication.run(urlshortApplication.class,args);
    }
}
