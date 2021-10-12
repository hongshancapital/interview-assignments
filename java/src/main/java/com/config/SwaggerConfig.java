package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {

//        ParameterBuilder ticketPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        ticketPar.name("token").description("登录校验")//name表示名称，description表示描述
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).defaultValue("Bearer ").build();//required表示是否必填，defaultvalue表示默认值
//        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controller"))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务Swagger")
                .description("短域名服务，详细信息......")
                .version("1.0")
                .termsOfServiceUrl("http://www.baidu.com/")
                .build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/doc/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/doc/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/doc/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/doc/swagger-resources", "/swagger-resources");
    }
}

