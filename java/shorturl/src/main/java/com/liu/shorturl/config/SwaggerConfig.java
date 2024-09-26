package com.liu.shorturl.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * Description： swagger2的配置
 *
 * @author liujiao
 * ConditionalOnProperty注解：是否开启swagger功能，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
 * Date: Created in 2021/11/11 17:13
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.liu.shorturl.controller")).paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("域名转换接口文档", "", "hgvghh@qq.com");
        return new ApiInfoBuilder()
                .title("长短域名转换")
                .description("1. 给定长域名转换为短域名；2. 给定短域名返回真实长域名")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
