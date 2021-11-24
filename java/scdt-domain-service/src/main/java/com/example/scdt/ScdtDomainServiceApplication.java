package com.example.scdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ScdtDomainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdtDomainServiceApplication.class, args);
    }

}
