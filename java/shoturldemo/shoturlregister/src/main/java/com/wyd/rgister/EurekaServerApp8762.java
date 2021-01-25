package com.wyd.rgister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp8762 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp8762.class, args);
    }
}
