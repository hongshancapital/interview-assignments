package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 首页 API 说明
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("API文档")
                .version("1.0.0")
                .build();
    }

    /**
     * swagger api 访问
     * <a href="http://localhost:8081/swagger-ui.html">Swagger地址</a>
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // controller 包下的所有类，生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build()
                ;
    }

}