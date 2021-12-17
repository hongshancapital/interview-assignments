package com.wf;

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
 *
 * swagger2接口说明:
 * http://地址:端口/应用名/swagger-ui.html
 * http://127.0.0.1:8080/swagger-ui.html
 *
 * swagger2接口源信息:
 * http://地址:端口/应用名/v2/api-docs
 * http://127.0.0.1:8080/v2/api-docs
 * JSON格式
 *
 */
/**
 * SwaggerConfig
 *  * swagger2接口说明:
 *  * http://地址:端口/应用名/swagger-ui.html
 *  * http://127.0.0.1:8080/swagger-ui.html
 *  *
 *  * swagger2接口源信息:
 *  * http://地址:端口/应用名/v2/api-docs
 *  * http://127.0.0.1:8080/v2/api-docs
 *  * JSON格式
 */
@Configuration  // 配置类
@EnableSwagger2 // 开启Swagger2的自动配置
public class SwaggerConfig {
    @Bean
    public Docket getUserDocket(){
        ApiInfo apiInfo=new ApiInfoBuilder()
                .title("用户管理")  //api标题
                .description("长短链接互转接口描述") //API描述
                .version("1.0.0") //版本号
                .license("WangFeng ： 2438128384@qq.com") //本API负责人的联系信息
                .build();
        return new Docket(DocumentationType.SWAGGER_2)//文档类型（swagger2）
                .apiInfo(apiInfo)   //设置包含在json ResourceListing响应中的api元信息
                .select()   //启动用于api选择的构建器
                .apis(RequestHandlerSelectors.basePackage("com.wf.controller")) //扫描接口的包
                .paths(PathSelectors.any()) //路径过滤器（扫描所有路径）
                .build();
    }
}
