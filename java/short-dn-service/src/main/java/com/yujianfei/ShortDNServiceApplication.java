package com.yujianfei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ShortDNServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortDNServiceApplication.class, args);
    }

}
