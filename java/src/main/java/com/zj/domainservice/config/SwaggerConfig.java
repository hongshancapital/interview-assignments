package com.zj.domainservice.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 //开启对Swagger2的支持
@Configuration //声明当前类是一个配置类
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private Boolean swaggerEnable;

    @Bean
    public Docket docket() {
        //链式编程（构建器模式），基本是固定
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo())
                .select()
                //扫描带有ApiOperation注解的所有方法，为它们生成API接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    //创建api的基本信息，自定义即可
    private ApiInfo apiInfo() {
        //链式编程（构建器模式），基本是固定
        return new ApiInfoBuilder()
                .title("长短域名接口文档")
                .description("SpringBoot集成Swagger2构建RESTful APIs")
                .termsOfServiceUrl("")
                .contact(new Contact("周峻","","zhoujun3309@126.com"))
                .version("1.0.0")
                .build();
    }

}
