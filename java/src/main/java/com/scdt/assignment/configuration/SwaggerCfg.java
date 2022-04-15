package com.scdt.assignment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @title SwaggerCfg.java
 * @description
 * @author
 * @date 2022-04-15 17:10:32
 */
@Configuration
@EnableOpenApi
public class SwaggerCfg implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)//
                .apiInfo(apiInfo())//
                .select()//
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//
                .paths(PathSelectors.any())//
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
                .title("接口文档")//
                .description("实现短域名服务")//
                .contact(new Contact("xiesx", "http://go168.xyz", "xiesx@foxmail.com"))//
                .version("1.0.0")//
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/").resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("forward:" + "/swagger-ui/index.html");
    }
}
