package com.diode.interview.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.diode.interview.*")
public class InterviewApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewApiApplication.class, args);
    }

}
