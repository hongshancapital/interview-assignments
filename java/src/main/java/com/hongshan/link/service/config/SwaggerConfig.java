package com.hongshan.link.service.config;

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
 * @author heshineng
 * created by 2022/8/8
 * swagger2 的配置相关
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("接口文档说明")
                .description("短连接转换服务接口")
                .version("1.0")
                .contact(new Contact("何世能", "http://localhost:8080/swagger-ui.html#/", "tiansu000@163.com"));
        ApiInfo apiInfo = apiInfoBuilder.build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                // 选择需要生成文档的接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongshan.link.service.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
