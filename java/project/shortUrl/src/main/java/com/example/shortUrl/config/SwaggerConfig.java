package com.example.shortUrl.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author HOPE
 * @Description swagger配置类
 * @Date 2022/4/29 9:40
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shortUrl"))
                .build().apiInfo(new ApiInfoBuilder()
                        .title("短域名服务")
                        .description("详细信息")
                        .version("1.0")
                        .build()
                );
    }
}

