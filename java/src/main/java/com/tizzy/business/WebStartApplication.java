package com.tizzy.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication(scanBasePackages = {"com.tizzy.business"}, exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan
public class WebStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class, args);
    }
}
