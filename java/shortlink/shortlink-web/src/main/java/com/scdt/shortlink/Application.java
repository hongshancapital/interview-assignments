package com.scdt.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring-boot启动类
 *
 * @Author tzf
 * @Date 2022/4/28
 */
@SpringBootApplication(scanBasePackages = {"com.scdt.shortlink"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
