package com.leo;

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

/**
 * swagger3 config
 * @author LeoZhang
 *
 */
@Configuration
public class Swagger3Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30)
//		return new Docket(DocumentationType.SPRING_WEB)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Swagger3接口文档")
				.description("更多请咨询listCode")
				.contact(new Contact("张东", "https://www.listcode.cn", "zdcin@qq.com"))
				.version("1.0")
				.build();
	}
}