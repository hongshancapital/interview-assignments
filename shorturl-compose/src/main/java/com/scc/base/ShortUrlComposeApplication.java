package com.scc.base;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author renyunyi
 * @date 2022/4/24 2:25 PM
 * @description project starter
 **/
@SpringBootApplication(scanBasePackages = "com.scc.base",exclude = MybatisAutoConfiguration.class)
public class ShortUrlComposeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlComposeApplication.class, args);
    }

}