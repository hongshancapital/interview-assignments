package com.scdt.interview.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class InterviewUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewUrlApplication.class, args);
    }

}
