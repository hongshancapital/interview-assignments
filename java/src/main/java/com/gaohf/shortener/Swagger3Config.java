package com.gaohf.shortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@PropertySource(value = {"classpath:/config/Swagger3.properties"})
@EnableOpenApi
@Configuration
public class Swagger3Config {

    @Value("${nature.restApi.enabled}")
    private Boolean enable;

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${application.description}")
    private String applicationDescription;

    @Value("${application.group01}")
    private String applicationGroup01;

    @Value("${application.apis.selector}")
    private String selector;


    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName(applicationGroup01)
                .select()
                .apis(RequestHandlerSelectors.basePackage(selector))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .version(applicationVersion)
                .build();
    }

}
