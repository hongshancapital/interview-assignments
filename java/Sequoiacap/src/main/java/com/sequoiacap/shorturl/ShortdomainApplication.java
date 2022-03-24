package com.sequoiacap.shorturl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口
 */
@SpringBootApplication
public class ShortdomainApplication {

    private final static Logger logger = LoggerFactory.getLogger(ShortdomainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShortdomainApplication.class, args);
    }

}
