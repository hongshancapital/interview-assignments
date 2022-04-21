package com.ittest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/21 10:54
 * @Description:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("----------------------------start success--------------------------------");
    }
}
