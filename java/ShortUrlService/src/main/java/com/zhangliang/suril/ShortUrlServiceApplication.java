package com.zhangliang.suril;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 短网址服务应用程序
 *
 * @author zhang
 * @date 2021/12/02
 */
@SpringBootApplication
@EnableWebMvc
public class ShortUrlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlServiceApplication.class, args);
    }

}
