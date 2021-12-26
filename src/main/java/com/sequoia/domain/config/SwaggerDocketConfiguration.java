package com.sequoia.domain.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerDocketConfiguration {

    @Bean
    public Docket doclet() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Short Domain API").version("Version 1.0.0").build();
        return new Docket(DocumentationType.SWAGGER_2).enable(true)
                .apiInfo(apiInfo)
                .select()
                .apis(withClassAnnotation(Api.class)).build();
    }
}