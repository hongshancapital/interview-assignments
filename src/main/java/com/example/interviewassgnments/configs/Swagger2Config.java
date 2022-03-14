/**
 * this is a test project
 */

package com.example.interviewassgnments.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2配制类
 * 通过@EnableOpenApi 注解开通Swagger2 3.0功能
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:13
 * @Description: com.example.interviewassgnments.configs
 * @version: 1.0
 */
@Configuration
@EnableOpenApi
public class Swagger2Config {

    @Value("${system.swagger2.basePackage}")
    private String basePackage;
    @Value("${system.swagger2.title}")
    private String title;
    @Value("${system.swagger2.description}")
    private String description;
    @Value("${system.swagger2.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${system.swagger2.version}")
    private String version;

    /**
     * 配置Swagger2相关的bean
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any()).build();
    }

    /**
     * API文档页面显示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version)
                .build();
    }
}
