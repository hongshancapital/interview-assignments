package com.wjup.shorturl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean //配置docket以配置Swagger具体参数
    public Docket docket1(Environment environment) {

        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .enable(true)//配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wjup.shorturl.controller"))
                .build()
                .groupName("短链接demo1")
                ;
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("niuxing", "", "18810694732@163.com");
        return new ApiInfo(
                "Swagger Api 在线接口文档", // 标题
                "短域名服务", // 描述
                "v1.0", // 版本
                "", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/swagger-ui.html")

                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars*")

                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}
