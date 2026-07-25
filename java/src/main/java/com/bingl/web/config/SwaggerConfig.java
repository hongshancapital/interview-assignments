package com.bingl.web.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置接口
     * @return
     */
    @Bean
    public Docket AppDriverDocket(Environment environment) {

        //设置只在开发中环境中启动swagger
        Profiles profiles= Profiles.of("dev","test","prd");

        //表示如果现在是dev环境，则返回true 开启swagger
        boolean flag=environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否启动swagger 默认启动
                .enable(true)
                //所在分组
                .groupName("短域名接口文档")
                .select()
                //指定扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.bingl.web"))
                // .paths(PathSelectors.any())
                //指定扫描的请求，这里表示扫描 /hello/ 的请求
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 配置ApiInfo信息
     * @return
     */
    private ApiInfo apiInfo() {

        //作者信息
        Contact author = new Contact("bingl", "", "");


        return new ApiInfo(
                "接口文档",
                "短域名接口",
                "1.0",
                "urn:tos",
                author,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );

    }
}
