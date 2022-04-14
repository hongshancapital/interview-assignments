package com.sequoiacap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sequoiacap.annotation.Generated;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 
 * @author zoubin
 */
@Configuration
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
@Generated
public class SwaggerConfig {
    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket( DocumentationType.OAS_30)
                .apiInfo( apiInfo() )
                .select()
                // (第一种方式)扫描所有有注解的api，用这种方式更灵活
                .apis( RequestHandlerSelectors.withMethodAnnotation( ApiOperation.class ) )
                // (第二种方式)扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.hubiao.pay.merchant.controller"))
                // (第三种方式)扫描所有 
                //.apis(RequestHandlerSelectors.any())
                .paths( PathSelectors.any() )
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title( "标题：短域名服务API文档" )
                .description( "描述：短域名存储接口：接受长域名信息，返回短域名信息, 短域名读取接口：接受短域名信息，返回长域名信息。" )
                .contact( new Contact("zoubin.zb", "", "crazyb20933666@163.com") )
                .version( "版本号:" + "V1.0.0" )
                .build();
    }
}