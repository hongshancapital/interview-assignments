package com.mhy.config;

import com.mhy.constant.DomainConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("短链接实现")
                        .description("# 将长链接转换为短链接")
                        .termsOfServiceUrl("http://" + DomainConstant.DOMAIN_NAME + "/domain/xx")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("mhy")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.mhy.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
