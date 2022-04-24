package com.scdt.job.lsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author lsx
 */
@SpringBootApplication
@EnableCaching
public class ScdtJobLsxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdtJobLsxApplication.class, args);
    }

}
