package com.heyenan.shorturldemo.config;

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
 * @author heyenan
 * @description Swagger相关配置
 *
 * @date 2020/5/07
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean    //依赖Spring注解
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")//使用2.0版本
                .apiInfo(apiInfo())        //创建api的基本信息，如：标题、描述、版本等
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.heyenan.shorturldemo.webservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务swagger API文档")
                .description("测试短域名服务接口文档")
                .version("1.0")
                .build();
    }
}
