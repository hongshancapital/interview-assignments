package com.scdt.domain.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author tuxiaozhou
 * @date 2022/4/2
 */
@Slf4j
@EnableOpenApi
@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
        log.info("short-domain-service started!");
    }

}
