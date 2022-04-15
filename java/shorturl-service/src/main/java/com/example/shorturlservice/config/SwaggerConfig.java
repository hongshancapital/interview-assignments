package com.example.shorturlservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description swagger配置
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket initSwaggerDocket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shorturlservice.controller"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务接口文档")
                .description("提供短域名接口的存储和读取")
                .version("1.0.0")
                .build();
    }
}
