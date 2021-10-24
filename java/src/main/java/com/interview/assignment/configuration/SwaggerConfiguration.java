package com.interview.assignment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

  @Bean
  public Docket docket(){
    return new Docket(DocumentationType.OAS_30)
      .apiInfo(apiInfo()).enable(true)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.interview.assignment"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo(){
    return new ApiInfoBuilder()
      .title("java-interview-assignment")
      .description("java-interview-assignment")
      .contact(new Contact("张旭峰", "https://my.oschina.net/zhangxufeng", "909012366@qq.com"))
      .version("1.0.0")
      .build();
  }

}
