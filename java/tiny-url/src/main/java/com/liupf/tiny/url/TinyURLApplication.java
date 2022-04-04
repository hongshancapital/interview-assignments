package com.liupf.tiny.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.liupf.tiny.url")
public class TinyURLApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyURLApplication.class, args);
    }

}
