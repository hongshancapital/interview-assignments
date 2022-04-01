package com.getao.urlconverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class UrlconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlconverterApplication.class, args);
		log.info("Service started!");
	}

}
