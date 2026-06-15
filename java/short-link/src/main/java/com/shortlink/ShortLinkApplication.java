package com.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.shortlink"
})
public class ShortLinkApplication{
    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class);
    }
}
