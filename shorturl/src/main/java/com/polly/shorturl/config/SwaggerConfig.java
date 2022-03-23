package com.polly.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author polly
 * @date 2022.03.22 22:14:23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.polly")
                )
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置Swagger信息,个性化设置,也可以用默认的
     *
     * @return
     */
    private ApiInfo getApiInfo() {
        Contact contact = new Contact("polly", "", "");
        ApiInfo apiInfo = new ApiInfo(
                "短域名服务",
                "",
                "1.0",
                "BG 5 company",
                contact,
                "2",
                "",
                new ArrayList<>()
        );
        return apiInfo;
    }
}
