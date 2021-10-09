package com.sequoiacap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: UrlSwagger2Configuration
 * @Description: url swagger配置
 * @Author: xulong.wang
 * @Date 10/10/2021
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class UrlSwagger2Configuration implements CommonSwagger2Configuration{

  @Bean
  public Docket urlRestApi() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("shortUrl")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sequoiacap.business.process.manager.controller"))
            .paths(PathSelectors.any())
            .build();
  }

}
