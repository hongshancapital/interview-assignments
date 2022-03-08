package com.findme.url.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("PIPENET-CRUD")
                .select()
                //告诉swagger2controller在哪里
                .apis(RequestHandlerSelectors.basePackage("com.findme.url.controller"))
                .paths(PathSelectors.any())// 这里面的所有路径
                .build().apiInfo(new ApiInfoBuilder()//构建
                        .description("接口文档的描述信息")
                        .title("域名转换接口文档")
                        // 联系人信息
                        .version("v1.0")
                        .license("Apache2.0")
                        .build());
    }
}
