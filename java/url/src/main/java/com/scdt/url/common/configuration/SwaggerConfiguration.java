package com.scdt.url.common.configuration;

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
 * Swagger 配置类
 * @author easten
 * @date 2021/12/09
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket createRestApi(){
        var swaggerPackageName = "com.scdt.url";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerPackageName))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo(){

        Contact contact = new Contact("Easten He", "", "eashe@qq.com");
        return new ApiInfoBuilder()
                .title("Url服务")
                .description("短链接的生成与查询")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
