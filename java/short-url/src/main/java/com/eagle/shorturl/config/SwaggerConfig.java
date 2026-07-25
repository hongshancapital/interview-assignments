package com.eagle.shorturl.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author eagle
 * @description http://localhost:8080/swagger-ui/index.html
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        RequestParameterBuilder header1 = new RequestParameterBuilder()
                .name("ts")
                .description("时间戳")
                .in(ParameterType.HEADER)
                .query(parameterSpecificationBuilder -> parameterSpecificationBuilder.defaultValue("1649694547448")
                        .allowEmptyValue(true));
        RequestParameterBuilder header2 = new RequestParameterBuilder()
                .name("sign")
                .description("签名")
                .in(ParameterType.HEADER)
                .query(parameterSpecificationBuilder -> parameterSpecificationBuilder.defaultValue("2c1e3f9656f511d0a279b4db9684929a6bffcaa868c69c12866ac7d160f23622")
                        .allowEmptyValue(true));
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder().title("短域名").description("短域名API").build())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eagle.shorturl.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(Lists.newArrayList(header1.build(), header2.build()));

    }

}
