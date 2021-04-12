package com.scdt.shorturl.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix="swagger")
class SwaggerConfig{
	@Value("${enable:true}")private Boolean enable=true;
	@Value("${base-package:'com.scdt'}")private String basePackage;
	@Value("${title:scdt}")private String title;
	@Value("${version:0.1}")private String version;
	@Value("${description:''}")private String description;
	@Value("${terms-of-service-url:'localhost'}")private String termsOfServiceUrl;
	@Value("${contact.name:scdt}")private String contactName;
	@Value("${contact.url:'http://127.0.0.1'}")private String contactUrl;
	@Value("${contact.email:'scdt@qq.com'}")private String contactEmail;
	/**
	 * 配置Docket对象
	 * @author 周小建
	 * @return Docket
	 */
	@Bean public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2).pathMapping("/").enable(enable).select()//是否开启
			.apis(RequestHandlerSelectors.any())//所有API接口类都生成文档
			.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
			.paths(PathSelectors.any())//指定路径处理PathSelectors.any()代表所有的路径
			.build()
			.apiInfo(new ApiInfoBuilder().title(title).description(description).termsOfServiceUrl(termsOfServiceUrl).contact(new Contact(contactName,contactUrl,contactEmail)).version(version).build());
    }
}