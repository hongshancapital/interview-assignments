package com.scdt.interview.assignments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.scdt.interview.assignments.**"})
public class AssignmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentsApplication.class, args);
    }

}
