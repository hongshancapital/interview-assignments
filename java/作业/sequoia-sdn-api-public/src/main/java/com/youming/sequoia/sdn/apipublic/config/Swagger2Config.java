package com.youming.sequoia.sdn.apipublic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.youming.sequoia.sdn.apipublic.controller"))
                .paths(PathSelectors.any()).build()
				/*
				.globalOperationParameters(		//全局配置，可设定path，param，header
	                    Arrays.asList(new ParameterBuilder()
	                        .name("app-authc")			//全局参数名称
	                        .description("用户令牌,从header中传入")		//全局参数描述
	                        .modelRef(new ModelRef("string"))	//值类型，integer,string		
	                        .parameterType("header")		//path(url),query(url查询字符串),form(表单),header(http头)
	                        .defaultValue("null") 		//默认值
	                        .required(false)
							.build()))*/
                ;
    }

    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("短域名-公共端API系统")
                // 描述
                .description("短域名-公共端API系统 接口说明")
                // 创建人
                .contact(new Contact("有铭", "abcbuzhiming@qq.com", ""))
                .version("1.0")
                .build();
    }
}
