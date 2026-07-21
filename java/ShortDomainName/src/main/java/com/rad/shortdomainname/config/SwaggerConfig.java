package com.rad.shortdomainname.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: Swagger的配置类,打开：http://localhost:port/swagger-ui.html
 * @date 2022-03-19 13:04:21
 */

@Configuration
public class SwaggerConfig {

    @Bean //作为bean纳入spring容器
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title("短域名服务接口文档")
                .description("短域名服务接口文档及相关接口的说明")
                .version("1.0.0")
                .build();
    }
}
