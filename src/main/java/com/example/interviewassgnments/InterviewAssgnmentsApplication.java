package com.example.interviewassgnments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = {"com.example.interviewassgnments.configs",
		"com.example.interviewassgnments.handler",
		"com.example.interviewassgnments.services",
		"com.example.interviewassgnments.controllers"})
public class InterviewAssgnmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewAssgnmentsApplication.class, args);
	}

}
