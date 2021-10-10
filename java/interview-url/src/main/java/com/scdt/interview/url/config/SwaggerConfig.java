package com.scdt.interview.url.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author: lijin
 * @date: 2021年10月10日
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scdt.interview.url.controller"))
                .apis(requestHandler -> requestHandler.isAnnotatedWith(ApiOperation.class))
                .build();
    }

}
