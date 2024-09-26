package com.thuangster.urlshortener.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.thuangster.urlshortener"))
				.paths(PathSelectors.ant("/api/*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("URL Shortener API")
				.description("URL Shortener for interview assignment")
				.contact(
						new Contact("Tong Huang", " ", "thuangster@icloud.com"))
				.version("1.0").build();
	}
}
