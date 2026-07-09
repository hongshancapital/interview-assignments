package com.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 全局配置
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String PACKAGE_PATH="com.domain.api";
    private String TITLE="域名接口";
    private String DESCRIPTION="域名接口";
    private String VERSION="1.0.0";

    @Bean
    public Docket createRestApi() {
        List<ResponseMessage> list=new ArrayList<ResponseMessage>();
        list.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(new ApiInfoBuilder()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE_PATH))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET,list)
                .globalResponseMessage(RequestMethod.POST,list)
                .globalResponseMessage(RequestMethod.OPTIONS,list);
    }

}
