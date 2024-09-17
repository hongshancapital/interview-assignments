package com.youyuzuo.shortdn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class DomainNameApplication {

    public static void main(String[] args){
        SpringApplication.run(DomainNameApplication.class);
    }
}
