package com.zhucan.domain.infrastructure.configuration;

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
 * @author zhuCan
 * @description
 * @since 2022/4/2 22:36
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

  @Bean
  public Docket adminServiceApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zhucan"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("红杉测试")
        .description(" Sequoia examine ")
        .license("")
        .licenseUrl("http://www.zhucan.com")
        .version("1.0")
        .build();
  }

}
