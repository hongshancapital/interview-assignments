package com.peogoo.common.config;


import com.peogoo.common.SrtingUtils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:    swagger-ui可视化接口配置类
 * @Author:         renxl
 * @CreateDate:     2021/4/15 15:30
 * @Version:        1.0
 */

@Configuration
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {
    @Value("${spring.profiles.active}")
    private String activeEv;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //选择controller包
                .apis(RequestHandlerSelectors.basePackage("com.peogoo")).paths(PathSelectors.any()).build()
                .enable(StringUtils.equalsIgnoreCase(activeEv,"dev")?true:false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //自定义信息可按需求填写
                .title("长地址转化成短地址")
                .description("")
                .termsOfServiceUrl("")
                .contact("长地址转化成短地址")
                .version("1.0")
                .build();
    }

}

