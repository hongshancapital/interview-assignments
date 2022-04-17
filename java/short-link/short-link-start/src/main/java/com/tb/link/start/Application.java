package com.tb.link.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@Slf4j
@SpringBootApplication(scanBasePackages={"com.tb.link"})
@ImportResource("classpath:spring.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("短链接域服务Application spring boot started!");
    }


}
