package com.sequoia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * TinyUrlApplication
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@EnableSwagger2
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.sequoia")
public class TinyUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyUrlApplication.class, args);
    }

}
