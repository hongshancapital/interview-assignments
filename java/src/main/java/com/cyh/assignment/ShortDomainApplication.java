package com.cyh.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.cyh.assignment")
public class ShortDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortDomainApplication.class, args);
	}

}
