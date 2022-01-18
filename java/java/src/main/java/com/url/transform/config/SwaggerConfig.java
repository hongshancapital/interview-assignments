package com.url.transform.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * @author xufei
 * @Description
 * @date 2021/12/10 10:57 AM
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).forCodeGeneration(true)
      // 指定package下生成API文档
      .select().apis(RequestHandlerSelectors.basePackage("com.url.transform"))
      // 过滤生成链接(any()表示所有url路径)
      .paths(PathSelectors.any()).build().apiInfo(apiInfo());
  }

  // api接口作者相关信息
  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfoBuilder().title("测试接口").version("1.0.0").build();
    return apiInfo;
  }

}