package com.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * 自己总结的Swagger2配置类模板
 *  swagger2URL: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean //配置docket以配置Swagger具体参数
    public Docket setDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(setApiInfo())
                /* 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口*/
                .select().apis(RequestHandlerSelectors.
//                        any() // 扫描所有，项目中的所有接口都会被扫描到
//                        .none() // 不扫描接口
//                      通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
//                        .withMethodAnnotation(final Class<? extends Annotation> annotation)
//                      通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
//                        .withClassAnnotation(final Class<? extends Annotation> annotation)
                        basePackage("com.task.controller"))
//              .paths(PathSelectors.ant("/king/**")) //设置过滤器,只扫描请求以/king开头的接口
                .build()
                .enable(true) //是否开启swagger2
                .groupName("江行");

    }

    //配置api的基本信息(这些信息会显示展示在index页面上)
    private ApiInfo setApiInfo(){

        Contact contact = new Contact("江行", "localhost", "1030486238@qq.com");
        return new ApiInfo(
                "江行的Java Assignment:长链接&短链接",// 标题(需要经常写)
                "游戏人生!",// 描述(需要经常写)
                "v1.0",// 版本
                "localhost",// 组织链接
                contact,// 联系人信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",// 许可连接
                new ArrayList<>()// 扩展信息
        );
    }
}
