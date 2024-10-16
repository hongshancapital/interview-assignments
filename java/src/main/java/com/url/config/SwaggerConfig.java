package com.url.config;
 
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
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
 * 开启对Swagger2的支持
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
 
    @Value("${swagger.enable}")
    private Boolean swaggerEnable;
 
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("长域名生成短域名服务接口文档")
                .description("长域名生成短域名服务 RESTful APIs")
                .version("1.0.0")
                .build();
    }
}