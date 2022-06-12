package com.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 *  * SwaggerConfig配置类
 *  * 通过@Configuration注解，让Spring来加载该类配置。
 *  * 再通过@EnableSwagger2注解来启用Swagger2。
 *
 * @Author: shican.sc
 * @Date: 2022/6/12 22:09
 * @see
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any()).build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("shican.sc java assignment")
                .description("@shican.sc java assignment")
                .termsOfServiceUrl("https://github.com/shicanLeft/interview-assignments")
                .version("1.0").build();
    }

}
