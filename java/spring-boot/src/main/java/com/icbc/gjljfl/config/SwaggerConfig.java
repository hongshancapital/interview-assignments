package com.icbc.gjljfl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.basePackage("com.icbc.gjljfl"))
                .paths(PathSelectors.any()).build().pathMapping("/");

    }
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用swagger2构建API文档")
                .description("欢迎访问api")
               // .contact(new Contact("nxzw","www.nxzw.com","nxzw@nxzw.com"))
                .version("V 1.0.0")
                .build();
    }
}
