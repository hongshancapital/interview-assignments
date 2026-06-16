package com.hongshang.shorturlweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.Contact;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * swagger配置设置类
 *
 * @author kobe
 * @date 2021/12/19
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * 生产swagger配置类
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongshang.shorturlweb"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("红杉短地址服务API")
                        .description("红杉短地址服务API_Swagger3")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new  Contact("","",""))
                        .license("The Apache License")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}

