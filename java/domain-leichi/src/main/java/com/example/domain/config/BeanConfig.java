package com.example.domain.config;

import com.example.domain.controller.DomainController;
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
 * Bean配置
 *
 * @author 雷池
 */
@Configuration
@EnableSwagger2
public class BeanConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(DomainController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务API")
                .description("短域名服务API by 雷池")
                .version("1.0")
                .contact(new Contact("雷池",
                        "https://github.com/thor-stack/interview-assignments/tree/master/java/domain-leichi",
                        "riseclei@hotmail.com"))
                .build();
    }

}
