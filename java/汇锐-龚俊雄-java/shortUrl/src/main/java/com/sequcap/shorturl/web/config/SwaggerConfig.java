package com.sequcap.shorturl.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("ShortUrl").description("Short URL generation")
        .version("0.1").build();
  }

  private PathProvider apiPathProvider() {
    return new AbstractPathProvider() {
      @Override
      protected String applicationPath() {
        return "/";
      }
      @Override
      protected String getDocumentationPath() {
        return "/";
      }
    };
  }

  @Bean
  public Docket userApi() {
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
          .pathProvider(apiPathProvider()).apiInfo(apiInfo()).select().build()
          .genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false);
    return docket;
  }

}
