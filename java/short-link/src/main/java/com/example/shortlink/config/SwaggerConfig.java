package com.example.shortlink.config;

import io.swagger.annotations.ApiOperation;
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
 * @author tianhao.lan
 * @date 2021-12-27.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket buildDocket() {
    // 要扫描的API(Controller)基础包
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf()).select().apis(
            RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any()).build();
  }

  private ApiInfo buildApiInf() {
    Contact contact = new Contact("lantianhao", "", "759183742@qq.com");
    return new ApiInfoBuilder().title("接口文档").description("接口文档").contact(contact)
        .version("1.0").build();
  }
}
