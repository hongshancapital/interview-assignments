package com.capital.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author jiangts
 * @Classname ConversionApplication
 * @Date 2021/4/19
 * @Version V1.0
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ConversionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversionApplication.class, args);
    }
}
