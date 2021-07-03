package com.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class ShortlinkandlonglinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortlinkandlonglinkApplication.class, args);
    }

}
