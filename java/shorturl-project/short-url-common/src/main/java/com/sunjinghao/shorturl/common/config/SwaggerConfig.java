package com.sunjinghao.shorturl.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 增强版swaggerUI
 * @author: zhucy
 * @create : 2019/4/30 16:57
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    /**
     *  0--关闭swagger
     *  1--开启swagger
     */
    @Value("${swagger.whether.open}")
    private int isOpen;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("")
                .select()
                // 带Api注解生成swagger文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        //关闭swagger；添加路径过滤，设置为全部都不符合
        if (isOpen == 0) {
            apis.paths(PathSelectors.none());
        }

        Docket docket = apis.build()
                /*.globalOperationParameters(pars)*/
                .apiInfo(apiInfo());
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        docket.globalResponseMessage(RequestMethod.GET, responseMessageList);
        docket.globalResponseMessage(RequestMethod.POST, responseMessageList);
        docket.globalResponseMessage(RequestMethod.PUT, responseMessageList);
        docket.globalResponseMessage(RequestMethod.DELETE, responseMessageList);
        return docket;
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
