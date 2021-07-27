package com.hsjt.example.netaddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(scanBasePackages={"com.hsjt.example.netaddress"})
@EnableOpenApi
@EnableScheduling
public class HsjtNetaddressApplication {

	public static void main(String[] args) {
		SpringApplication.run(HsjtNetaddressApplication.class, args);
	}

}
