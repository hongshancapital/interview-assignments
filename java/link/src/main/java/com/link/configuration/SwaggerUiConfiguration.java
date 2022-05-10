package com.link.configuration;

import com.link.LinkApplication;
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
 * swagger-ui 配置信息
 *
 * @auth zong_hai@126.com
 * @date 2022-04-15
 * @desc
 */
@EnableSwagger2
@Configuration
public class SwaggerUiConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage(LinkApplication.class.getPackage().getName()))
                .apis(RequestHandlerSelectors.basePackage("com.link.web"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("link")
                .description("Link Swagger2 API </br> 长域名生成短域名和短域名接查询长域名").version("1.0").build();
    }
}
