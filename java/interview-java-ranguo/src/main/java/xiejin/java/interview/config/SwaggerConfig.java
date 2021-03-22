package xiejin.java.interview.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
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
import xiejin.java.interview.result.ResultJson;

/**
 * @author xiejin
 * @date 2019/4/23 14:21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    private Contact contact = new Contact("domain-api", "http://127.0.0.1:8088/doc.html", "https://**");

    //    用@Configuration注解该类，等价于XML中配置beans；用@Bean标注方法等价于XML中配置bean。
//    @Profile(value = "local")
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("restfulAPI")
                .genericModelSubstitutes(ResultJson.class)
                .enable(swaggerEnabled)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("xiejin.java.interview"))
//                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("域名长短变换文档")
                .contact(contact)
                .description("域名长短变换文档")
                .termsOfServiceUrl("http://www.nothing.com")
                .version("1.0.0")
                .build();
    }
}
