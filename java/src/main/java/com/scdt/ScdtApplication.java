package com.scdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @Author: lenovo
 * @since: 2021-12-15
 */
@SpringBootApplication(scanBasePackages = "com.scdt")
public class ScdtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScdtApplication.class);
    }
}
