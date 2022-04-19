package com.mortimer.shortenurl;

import com.mortimer.shortenurl.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class ShortenurlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenurlApplication.class, args);
    }

}
