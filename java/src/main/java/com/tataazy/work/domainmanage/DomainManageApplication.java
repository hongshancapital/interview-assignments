package com.tataazy.work.domainmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class DomainManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainManageApplication.class, args);
    }

}
