package com.scdt.yulinfu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@SpringBootApplication(scanBasePackages = {"com.scdt.yulinfu"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
