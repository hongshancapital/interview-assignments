package com.scdt.china.shorturl.configuration;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * Swagger文档配置
 *
 * @author ng-life
 */
@Configuration
public class SwaggerDocketConfiguration {

    @Bean
    public Docket doclet() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("短地址服务API").version("1.0.0").build();
        return new Docket(DocumentationType.OAS_30).enable(true)
                .apiInfo(apiInfo)
                .select()
                .apis(withClassAnnotation(Api.class)).build();
    }

}
