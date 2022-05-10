package com.hongshan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //swagger测试的controller路径
                .apis(RequestHandlerSelectors.basePackage("com.hongshan.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //在测试界面简单进行描述
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APIs")
                .description("api接口文档")
                .version("1.0")
                .build();
    }
}
