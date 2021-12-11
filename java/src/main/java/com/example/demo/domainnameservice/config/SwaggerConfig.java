package com.example.demo.domainnameservice.config;


import org.springframework.beans.factory.annotation.Value;
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
 * swagger2 configuration.
 *
 * @author laurent
 * @date   2021/12/11 上午10:03
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enabled:true}")
    private boolean enableSwagger;

    @Bean
    @SuppressWarnings("SpellCheckingInspection")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.domainnameservice"))
                .paths(PathSelectors.any())
                .build();
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Slim domain name service illustration。")
                .description("Including long domain name shorten and slim domain name restoration.")
                .version("1.0")
                .build();
    }
}
