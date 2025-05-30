package com.example.app.common.utils.swagger;

import io.swagger.annotations.ApiOperation;
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
 * Swagger2配置项
 *
 * @author voidm
 */
@Configuration
@EnableSwagger2
public class Swagger2Conf {

    @Bean
    public Docket getUserDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("短域名服务")
                .description("短域名服务类相关接口")
                // 版本号
                .version("1.0.0")
                // 本API负责人的联系信息
                .contact(new Contact("voidm", "https://github.com/marlkiller", "marlkiller@hotmail.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置包含在json ResourceListing响应中的api元信息
                .apiInfo(apiInfo)
                // 启动用于api选择的构建器
                .select()
                // 扫描接口的包
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 路径过滤器（扫描所有路径）
                .paths(PathSelectors.any())
                .build();
    }
}