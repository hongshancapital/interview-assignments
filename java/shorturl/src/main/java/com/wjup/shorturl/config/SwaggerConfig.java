package com.wjup.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean //配置docket以配置Swagger具体参数
    public Docket docket1(Environment environment) {

        //因只做demo  未区分项目实际部署环境
        //当前所有环境配置
//        Profiles of = Profiles.of("dev", "test");

        //获取当前环境
//        boolean b = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .enable(true)//配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wjup.shorturl.controller"))
                .build()
                .groupName("短链接demo1")
                ;
    }

    /*@Bean //配置docket以配置Swagger具体参数
    public Docket docket2(Environment environment) {

        //当前所有环境配置
//        Profiles of = Profiles.of("dev", "test");

        //获取当前环境
//        boolean b = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .enable(true)//配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()
                //any() // 扫描所有，项目中的所有接口都会被扫描到
                //none() // 不扫描接口
                // 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
                //withMethodAnnotation(final Class<? extends Annotation> annotation)
                // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
                //withClassAnnotation(final Class<? extends Annotation> annotation)
                //basePackage(final String basePackage) // 根据包路径扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.huaxiapawn.payment.controller.aliyun"))
                //接口过滤
                //any() // 任何请求都扫描
                //none() // 任何请求都不扫描
                //regex(final String pathRegex) // 通过正则表达式控制
                //ant(final String antPattern) // 通过ant()控制
                //.paths(PathSelectors.ant("/kuang/**"))
                .build()
                .groupName("阿里云")
                ;
    }*/

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

    /**
     * swagger-ui.html路径映射，浏览器中使用/api-docs访问
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs","/swagger-ui.html");
    }

}
