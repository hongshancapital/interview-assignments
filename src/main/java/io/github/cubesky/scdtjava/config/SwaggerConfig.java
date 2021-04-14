package io.github.cubesky.scdtjava.config;

import io.github.cubesky.scdtjava.ScdtJavaApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger API 设置
 */
@Configuration
public class SwaggerConfig {
    /**
     * 设置 Swagger API
     * @return Docket 设置
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(ScdtJavaApplication.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建并返回 API 信息
     * @return Swagger Api 信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "UrlShorten",
                "UrlShorten for scdt",
                "0.0.1",
                "#",
                new Contact("汪沁然", "https://liyin.party", "edge4chaos+scdt.job@gmail.com"),
                "", "", Collections.emptyList());
    }

}
