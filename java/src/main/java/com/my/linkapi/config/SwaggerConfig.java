package com.my.linkapi.config;

import org.springframework.boot.SpringBootVersion;
import springfox.documentation.service.ApiInfo;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.HashSet;

@Component
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        HashSet hashSet = new HashSet<String>();
        hashSet.add("https");
        hashSet.add("http");
        return new Docket(
                DocumentationType.OAS_30)
                .pathMapping("/")
                .groupName("张*")
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(true)
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 接口调试地址
                .host("http://localhost:8888")
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(hashSet);
    }

    /**
     * API 页面上半部分展示信息
     * 可以new ApiInfo对象，到ApiInfo里面去看源码
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("xxx系统API说明")
                .description("springfox swagger 3.0")
                .version("Application Version: 1.0, Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }
}
