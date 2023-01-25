package url.converter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("url.converter.facade.controller")).paths(PathSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("url converter api")
                // 创建人信息
                .contact(new Contact("WangSiqi", "", "w47happy@foxmail.com"))
                // 版本号
                .version("1.0.0")
                // 描述
                .description("url converter api documentation")
                .build();
    }
}
