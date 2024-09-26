package org.demo.shortlink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wsq
 * @date 2022/3/26 16:55
 * @description:
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.demo.shortlink.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("短链服务接口文档")
                .description("可根据长链生成对应短链, 并根据短链查询长链的服务")
                .contact(new Contact("xxxxxxiong", "", "xsj1145421439@outlook.com"))
                .version("1.0")
                .build();
    }
}
