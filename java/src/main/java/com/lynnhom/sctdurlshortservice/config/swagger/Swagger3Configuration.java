package com.lynnhom.sctdurlshortservice.config.swagger;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
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
 * @description: Swagger配置
 * @author: Lynnhom
 * @create: 2021-11-08 20:08
 **/
@Configuration
@EnableOpenApi
public class Swagger3Configuration {
    /**
     * 控制是否开启swagger，区分环境使用
     */
    @Value("${swaggerInfo.enable}")
    private Boolean enable;

    /**
     * 项目应用名
     */
    @Value("${swaggerInfo.name}")
    private String appName;

    /**
     * 项目版本信息
     */
    @Value("${swaggerInfo.version}")
    private String appVersion;

    /**
     * 服务端口
     */
    @Value("${swaggerInfo.port}")
    private String port;

    /**
     * 项目描述信息
     */
    @Value("${swaggerInfo.description}")
    private String appDesc;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .protocols(Sets.newHashSet("https", "http"));
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(appName + " Api Doc")
                .description(appDesc)
                .version("App Version: " + appVersion + ", Spring Boot Version: "
                        + SpringBootVersion.getVersion())
                .build();
    }
}
