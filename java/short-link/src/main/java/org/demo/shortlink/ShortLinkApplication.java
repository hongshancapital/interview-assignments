package org.demo.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangsiqi
 * @date 2021/10/24
 * @description
 */

@SpringBootApplication
public class ShortLinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class, args);
    }
}
