package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com"})
public class ShortDomainUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortDomainUrlApplication.class, args);
    }

}
