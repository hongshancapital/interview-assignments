package com.nbasoccer.shorturl.config;

import com.nbasoccer.shorturl.dto.ResponseCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2//开启Swagger
public class SwaggerConfig {

    //配置Swagger的bean实例
    @Bean
    public Docket docketA() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResponseCode.values()).forEach(stateCodeEnum ->
                responseMessageList.add(
                        new ResponseMessageBuilder()
                                .code(stateCodeEnum.getCode())
                                .message(stateCodeEnum.getMessage())

                                .build()));
        return new Docket(DocumentationType.SWAGGER_2)
                //添加全局状态码
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .enable(true)//是否启用swagger，如果为false则swagger不能再浏览器中访问
                .select()//通过select()方法配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                //指定扫描的api包
                .apis(RequestHandlerSelectors.basePackage("com.nbasoccer.shorturl.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("我", "", "44054730@qq.com");
        return new ApiInfo(
                "Short Url API文档",
                "短链接服务",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
