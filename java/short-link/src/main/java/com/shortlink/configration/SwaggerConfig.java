package com.shortlink.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select().paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.shortlink"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("短域名接口文档")
                .description("短域名存储、读取相关接口").version("0.0.1")
                .build();
    }
}
