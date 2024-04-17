package com.david.urlconverter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.ui.switch}")
    private boolean swagger_ui_switch;

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 默认值为true, false则关闭Swagger,可读取配置文件的环境状态(dev/produce),切换是否开启swagger
                .enable(swagger_ui_switch)
                .select().apis(RequestHandlerSelectors.basePackage("com.david.urlconverter"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置Swagger的信息：apiInfo   修改Swagger的主页面展示信息
     */
    private ApiInfo apiInfo(){
        Contact DEFAULT_CONTACT = new Contact("测试者", "http://localhost:8080/", "1246145143@qq.com");
        return new ApiInfo("David Api Documentation",
                "David Api Documentation",
                "1.0",
                "http://localhost:8080/",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}