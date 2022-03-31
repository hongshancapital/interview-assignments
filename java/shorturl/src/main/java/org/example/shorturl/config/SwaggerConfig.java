package org.example.shorturl.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.properties.SwaggerProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 自动加载bean的配置对象。该对象包含swagger以及knife4j配置
 *
 * @author bai
 * @date 2021-01-14
 */
@Slf4j
@Configuration
@EnableKnife4j
@EnableOpenApi
@AllArgsConstructor
@EnableConfigurationProperties({SwaggerProperty.class})
public class SwaggerConfig {
    
    /**
     * swagger配置
     *
     * @param property 属性
     * @return {@link Docket}
     */
    @Bean
    public Docket rotbotDocket(SwaggerProperty property) {
        ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(property))
                .select();
        // 扫描指定的包
        apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(property.getBasePackage()));
        
        //分组
        return apiSelectorBuilder.paths(PathSelectors.any())
                                 .build();
    }
    
    /**
     * 获取api的信息
     *
     * @param property 属性
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo(SwaggerProperty property) {
        return new ApiInfoBuilder()
                .title(property.getTitle())
                .description(property.getDescription())
                .version(property.getVersion())
                .license(property.getLicense())
                .licenseUrl(property.getLicenseUrl())
                .termsOfServiceUrl(property.getTermsOfServiceUrl())
                .contact(new Contact(property.getContact().getName(),
                                     property.getContact().getUrl(),
                                     property.getContact().getEmail()))
                .build();
    }
}
