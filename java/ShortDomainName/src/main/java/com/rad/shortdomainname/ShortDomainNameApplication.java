package com.rad.shortdomainname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class ShortDomainNameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortDomainNameApplication.class, args);
    }

}
