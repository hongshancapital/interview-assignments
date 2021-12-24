/* 
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：Swagger2Config.java
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	  lli   2017年8月2日下午5:00:19	       Create	
 */
package com.yuanyang.hongshan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置 如果不想开启把下面的注解注释掉
 * 
 * @author yuanyang
 *
 * @version 1.0
 * 
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.yuanyang.hongshan.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("我的业务系统服务")
                .description("我的业务系统服务").version("1.0").build();
    }

}
