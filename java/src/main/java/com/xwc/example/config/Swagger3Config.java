package com.xwc.example.config;

import com.xwc.example.commons.model.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：接口文档声明
 * 作者：徐卫超 (cc)
 * 时间 2022/4/10 21:31
 */

@Configuration
public class Swagger3Config {

    @Bean
    public Docket docket() {
        List<Response> responses = globalResponses();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(true)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.POST, responses)
                .globalResponses(HttpMethod.PUT, responses)
                .globalResponses(HttpMethod.DELETE, responses)
                .select()
                //添加swagger接口提取范围,修改成指向你的controller包
                .apis(RequestHandlerSelectors.basePackage("com.xwc.example"))
                .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringCloud2020接口文档")
                .description("这是一个接口文档")
                .contact(new Contact("xuweichao010", "xuweichao010", "xuweichao010@163.com"))
                .version("1.0")
                .build();
    }

    public List<Response> globalResponses() {
        List<Response> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(Result.SUCCESS)).description("操作成功").build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(Result.FAILED)).description("业务逻辑错误").build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(Result.FORBIDDEN)).description("资源访问受限").build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(Result.NOT_AUTHEN)).description("未认证").build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(Result.ERROR)).description("服务器内部错误").build());
        return responseMessageList;
    }

}
