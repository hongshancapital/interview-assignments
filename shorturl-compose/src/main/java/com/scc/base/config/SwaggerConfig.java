package com.scc.base.config;

import io.swagger.annotations.Api;
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
 * @author renyunyi
 * @date 2022/4/26 1:00 下午
 * @description TODO
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("shorturl api doc")
                .description("shorturl api doc")
                .termsOfServiceUrl("http://localhost:8080/shorturl-compose")
                .version("1.0.0")
                .build();
    }

}
