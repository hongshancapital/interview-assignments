package com.hs.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class, args);
    }

}
