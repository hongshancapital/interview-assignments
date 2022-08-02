package com.xinerde.demo.shortlink.web.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



/**
 * Springboot启动类
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
@ComponentScan(basePackages = {"com.xinerde.demo.shortlink"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
