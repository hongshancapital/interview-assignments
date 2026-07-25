package com.hs.wl.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Value("${swagger.enable}")
	private Boolean swaggerEnable;

	@Bean
	public Docket docket(){
		Docket docket = null;
		try {
			docket = new Docket(DocumentationType.SWAGGER_2)
					.pathMapping("/")
					.select()
					//.apis(RequestHandlerSelectors.basePackage("com.hs.wl.controller"))
					.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
					.paths(PathSelectors.any())
					.build()
					.apiInfo(new ApiInfoBuilder()
							.title("长短链接服务文档")
							.description("长短链接服务文档")
							.version("1.0.0")
							.contact(new Contact("wl", "baidu.com", "xxx@xx"))
							.license("The Apache License")
							.licenseUrl("http://www.baidu.com")
							.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docket;
	}

}
