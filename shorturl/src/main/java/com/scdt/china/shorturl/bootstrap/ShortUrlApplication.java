package com.scdt.china.shorturl.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scdt.china.shorturl")
public class ShortUrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class, args);
    }
}
