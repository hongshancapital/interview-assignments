package com.zhangliang.suril.configuration;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 的配置
 *
 * @author zhang
 * @date 2021/12/02
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建rest api 文档
     *
     * @return {@link Docket}
     */
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("面试项目");
        builder.description("面试项目");
        builder.contact(new Contact("张亮", "https://www.zxy168.xyz", "48906088@qq.com"));
        builder.version("6.6.6");
        ApiInfo info = builder.build();
        docket.apiInfo(info);

        ApiSelectorBuilder selectorBuilder = docket.select();
        selectorBuilder.paths(PathSelectors.any());
        selectorBuilder.apis(RequestHandlerSelectors.basePackage("com.zhangliang.suril"));
        docket = selectorBuilder.build();

        return docket;
    }
}

