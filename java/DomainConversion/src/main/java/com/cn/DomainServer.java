package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.cn")
public class DomainServer {
    public static void main(String[] args) {
        SpringApplication.run(DomainServer.class, args);
    }
}
