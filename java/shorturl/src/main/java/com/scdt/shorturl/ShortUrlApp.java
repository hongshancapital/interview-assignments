package com.scdt.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 短URL服务应用启动类
 * @author niuyi
 * @since  2021-12-10
 */
@SpringBootApplication
@EnableSwagger2
public class ShortUrlApp {
    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApp.class, args);
    }
}
