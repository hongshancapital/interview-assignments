package com.sequoia.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.sequoia.domain")
@EnableSwagger2
public class ShortDomainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortDomainServiceApplication.class, args);
    }

}
