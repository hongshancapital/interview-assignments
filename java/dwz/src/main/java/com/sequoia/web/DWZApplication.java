package com.sequoia.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class DWZApplication {
    public static void main(String[] args) {
        SpringApplication.run(DWZApplication.class);
    }
}
