package com.hsjt.example.netaddress.swagger.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * Swagger3API文档的配置
 * @author dazzling
 *
 */
@Configuration
@EnableOpenApi
@ConditionalOnProperty(name = "swagger.swhitch", havingValue = "true")
public class Swagger3Config {
	
	// 默认关闭
	@Value(value = "${swagger.swhitch:false}")
	private boolean swaggerSwhitch;
	// 默认标题
	@Value(value = "${swagger.title:Swagger3接口文档}")
	private String title;
	// 默认描述
	@Value(value = "${swagger.description:模块咨询}")
	private String description;
	// 默认版本
	@Value(value = "${swagger.version:1.0}")
	private String version;
	
	

	@Bean
    public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30)
				.enable(swaggerSwhitch)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
	
	private List<SecurityScheme> securitySchemes() {
		// (List<SecurityScheme>  List<ApiKey>
        List<SecurityScheme> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        apiKeyList.add(new ApiKey("username", "username", "header"));
        return apiKeyList;
    }
	
	
	 private List<SecurityContext> securityContexts() {
	        List<SecurityContext> securityContexts=new ArrayList<>();
	        securityContexts.add(
	                SecurityContext.builder()
	                        .securityReferences(defaultAuth())
	                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
	                        .build());
	        return securityContexts;
	    }
	 
	 List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        List<SecurityReference> securityReferences=new ArrayList<>();
	        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
	        securityReferences.add(new SecurityReference("username", authorizationScopes));
	        securityReferences.add(new SecurityReference("x-auth-token", authorizationScopes));
	        return securityReferences;
	    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact("Dazz。", "http://www.ruiyeclub.cn", "ruiyeclub@foxmail.com"))
                .version(version)
                .build();
    }
	
	 

}
