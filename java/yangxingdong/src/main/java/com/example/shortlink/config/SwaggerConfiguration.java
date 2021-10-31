package com.example.shortlink.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger integrated with spring boot  ref doc : https://www.cnblogs.com/zs-notes/p/10845741.html
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerConfiguration {

    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.shortlink.controller"))
                .build();
    }
    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Short Link Doc")
                // 创建人信息
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述")
                .build();
    }




    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();


        // Equivalent to your Google thread:
        // builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

        return builder;
    }

}
