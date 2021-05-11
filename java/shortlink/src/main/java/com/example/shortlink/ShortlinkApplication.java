package com.example.shortlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShortlinkApplication {
    private static Logger logger = LoggerFactory.getLogger(ShortlinkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShortlinkApplication.class, args);
        logger.info("app is running");
    }

}
