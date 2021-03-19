package com.sxg.shortUrl.config;

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
 * 
 * @author sxg 2.0 访问链接 http://localhost:8080/swagger-ui.html 3.0 访问链接
 *         http://localhost:8080/swagger-ui/index.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30)
				// 定义是否开启swagger，false为关闭，可以通过变量控制
				.enable(true).select()
				// 配置需要扫描的controller位置
				.apis(RequestHandlerSelectors.basePackage("com.sxg.shortUrl.controller"))
				// 配置路径
				.paths(PathSelectors.any())
				// 构建
				.build();
	}

	// 文档信息
	@SuppressWarnings("unused")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Demo接口文档").description("短链接功能").contact(new Contact("sxg", "url", "email"))
				.version("1.0").build();
	}
}
