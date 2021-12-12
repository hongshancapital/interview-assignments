package com.scdt.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.scdt.shortlink")
@EnableWebMvc
public class ShortlinkApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShortlinkApplication.class, args);
    }
}
