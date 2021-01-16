package com.ascmix.surl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SurlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurlApplication.class, args);
    }

}
