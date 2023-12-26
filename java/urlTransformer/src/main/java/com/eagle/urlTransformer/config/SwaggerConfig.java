package com.eagle.urlTransformer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/** 
 * @ClassName: SwaggerConfig 
 * @Description: Swagger配置类
 * @author Eagle
 * @date 2022年1月18日 下午6:30:42 
 *  
 */
@Configuration
@EnableOpenApi
@Profile({"dev"})
public class SwaggerConfig {
	 	@Bean
	    public Docket createRestApi() {
	        return new Docket(DocumentationType.OAS_30)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.eagle.urlTransformer.controller"))
	                .paths(PathSelectors.any())
	                .build();
	    }
	 
	 //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                //页面标题
	                .title("长、短链接转换工具")
	                //创建人
	                .contact(new Contact("Eagle", "http://www.eagle.com", "29818470@qq.com"))
	                //版本号
	                .version("-0.1")
	                //描述
	                .description("长、短链接转换工具接口文档")
	                .build();
	    }
}
