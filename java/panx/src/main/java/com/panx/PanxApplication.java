package com.panx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com"})
@EnableSwagger2
public class PanxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanxApplication.class, args);
    }

}
