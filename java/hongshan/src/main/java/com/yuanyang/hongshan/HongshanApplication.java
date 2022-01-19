package com.yuanyang.hongshan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HongshanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HongshanApplication.class, args);
    }
}
