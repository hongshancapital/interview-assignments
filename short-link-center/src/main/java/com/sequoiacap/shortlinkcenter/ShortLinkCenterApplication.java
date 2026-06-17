package com.sequoiacap.shortlinkcenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication(scanBasePackages = {"com.sequoiacap"})
@EnableScheduling
@EnableAsync
@EnableOpenApi
@Slf4j
public class ShortLinkCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortLinkCenterApplication.class, args);
		log.info("====================> ShortLinkCenterApplication start successful <====================");	}

}
