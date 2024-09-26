package com.test.shorturl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * @Author: liurenyuan
 * @Date: 2021/11/10
 * @Version: 1.0
 */
@Configuration
public class Configer {
    @Value("${writableStackTrace:false}") //是否打印异常堆栈信息，默认不打印
    public Boolean  writableStackTrace;
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error.*).*"))//Swagger去掉basic-error-controller
                .build();
    }

}
