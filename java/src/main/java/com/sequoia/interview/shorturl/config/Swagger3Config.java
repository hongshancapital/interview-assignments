package com.sequoia.interview.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger3Config {
  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.OAS_30).apiInfo(
        new ApiInfoBuilder().contact(new Contact("JamesXu", "", "jamesxxx@aliyun.com")).title("短网址生成项目").build());
  }

}