package com.sequoia.shorturl;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableSwagger2Doc
@EnableAsync
@EnableRetry
@EnableCaching
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SpringBootShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootShortUrlApplication.class, args);
    }
}

