package com.zoujing.shortlink.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("shortLink api 说明")
                        .contact(new Contact("wustzoujing", "https://www.yuque.com/docs/share/3d7f9c1d-3a27-4147-a679-b2d4dc31dc1e?#%20%E3%80%8A%E7%9F%AD%E9%93%BE%E6%9C%8D%E5%8A%A1%E8%AE%BE%E8%AE%A1%E6%96%B9%E6%A1%88%E3%80%8B", "164325433@qq.com"))
                        .version("1")
                        .termsOfServiceUrl("localhost:8080/shortLink/")
                        .description("shortLink demo api")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zoujing.shortlink.controller"))
                .build();
    }
}
