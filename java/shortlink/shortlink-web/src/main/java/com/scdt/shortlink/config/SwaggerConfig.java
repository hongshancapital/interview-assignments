package com.scdt.shortlink.config;

import lombok.extern.slf4j.Slf4j;
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
 * Swagger Bean配置
 *
 * @Author tzf
 * @Date 2022年05月02日 06:13
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        log.info("进入到swagger的配置中");
        return new Docket(DocumentationType.SWAGGER_2)
            // 指定构建api文档的详细信息的方法：apiInfo()
            .apiInfo(apiInfo())
            .select()
            // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
            .apis(RequestHandlerSelectors.basePackage("com.scdt.shortlink"))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * 构建api文档的详细信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            // 设置页面标题
            .title("长链转短链接口总览")
            // 设置接口描述
            .description("长链转短链接口、短链获取长链接口")
            // 设置联系方式
            .contact(new Contact("陶紫峰", "http://localhost:8080/", "2674090@qq.com"))
            // 设置版本
            .version("1.0")
            // 构建
            .build();

    }
}
