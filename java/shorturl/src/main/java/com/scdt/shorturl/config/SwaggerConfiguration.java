package com.scdt.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
* @description Swagger配置类
* @author Leo
* @date 2022/3/2 16:42
**/
@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

    /**
    * @description: 配置 SwaggerBean
    * @param
    * @return: Docket
    * @author: Leo
    * @date: 2022/3/2 16:47
    */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scdt.shorturl.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
    * @description: apiInfo
    * @param
    * @return:
    * @author: Leo
    * @date: 2022/3/2 16:48
    */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档测试")
                .description("文档描述：更多问题，请联系开发者")
                .contact(new Contact("Leo", "https://www.scdt.cn", "fengyunli@163.com"))
                .version("1.0")
                .build();
    }
}