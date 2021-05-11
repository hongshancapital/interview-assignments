package com.wyd.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

@Component
@EnableDiscoveryClient
public class GetWapApplication9000 {

    public static void main(String[] args) {
        SpringApplication.run(GetWapApplication9000.class, args);
    }
}
