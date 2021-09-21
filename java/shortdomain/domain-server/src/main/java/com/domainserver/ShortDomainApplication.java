package com.domainserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 短域名服务启动类
 *
 * @author 905393944@qq.com
 * @Date 2021/9/21
 */
@SpringBootApplication
@EnableSwagger2
public class ShortDomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortDomainApplication.class, args);
    }

}
