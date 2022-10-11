package com.java.assignment.web.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger配置类
 *
 * @author DUCHONG
 * @since 2020-10-26 9:34
 **/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket initDocket(Environment env) {

        //设置要暴漏接口文档的配置环境
        Profiles profile = Profiles.of("dev", "test");
        boolean flag = env.acceptsProfiles(profile);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JacyJavaAssignment")
                .description("作业")
                .contact(new Contact("jacy", "https://github.com/jaycGong", "570732558@qq.com "))
                .version("1.0")
                .build();
    }
}
