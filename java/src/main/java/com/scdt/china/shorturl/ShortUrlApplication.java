package com.scdt.china.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 短地址应用入口
 *
 * @author ng-life
 */
@SpringBootApplication
public class ShortUrlApplication {

    public static void main(String[] args) {
        new SpringApplication(ShortUrlApplication.class).run(args);
    }

}
