package com.scdt.shorturl.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 配置
 * @author niuyi
 * @since  2021-12-10
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scdt.shorturl.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("java面试")
                        .description("java面试")
                        .version("1.0")
                        .contact(new Contact("牛一","https://gitee.com/fencer911/","knightyi@qq.com")
                        )
                        .license("The Apache License")
                        .licenseUrl("https://gitee.com/fencer911/")
                        .build());
    }
}


