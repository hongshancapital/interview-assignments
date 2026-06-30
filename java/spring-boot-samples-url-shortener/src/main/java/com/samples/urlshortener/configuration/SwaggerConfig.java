package com.samples.urlshortener.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @Author: liuqu
 * DateTime: 2022年04月17日 13时42分
 * Function:
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {


    @Bean
    public Docket publicApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiPublicInfo())
                .groupName("API列表C-公共")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.samples.urlshortener.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiPublicInfo() {
        return new ApiInfoBuilder()
                .title("短链接公共API文档")
                .description("公共API目前只有服务健康监测api")
                .termsOfServiceUrl("http://127.0.0.1:9527")
                .version("V1.0、V2.2")
                .build();
    }
}
