package com.sequoia.shorturl.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

/*
    @Bean
    public Docket createRestApi() {
        // 文档信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 查询
                .select()
                // 注解包的路径
                .apis(RequestHandlerSelectors.basePackage("com.sequoia.shorturl.web.controller"))
                // 任何路径
                .paths(PathSelectors.any())
                .build();
    }
*/

    @Bean
    public Docket createH5RestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务")
                .description("该服务提供长域名转短域名和短域名跳转长域名的功能")
                .version("v1")
                .build();
    }

}