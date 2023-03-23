package com.xaviwang.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * This is a class to start the tiny URL service. 
 */
@SpringBootApplication
public class TinyUrlApplication {
	/**
	 * Simply run this application to start the tiny URL service.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TinyUrlApplication.class, args);
	}

}
