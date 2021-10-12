package com.jk.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置 swagger 生成在线 api 文档
 *
 * @author Jiang Jikun
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.jk.shorturl.controller";
    public static final String VERSION = "1.0.0";

    /**
     * 配置 swagger 的 docket 的 bean 实例
     */
    @Bean
    public Docket createRestApi(Environment environment) {
        //设置要显示的 swagger 环境，只有 开发和测试环境才开启 swagger，正式环境不开启
        Profiles profiles = Profiles.of("dev", "test");
        //通过 environment.acceptsProfiles 判断当前所处的环境是否是上文设定的环境，是则返回 true；
        boolean flag = environment.acceptsProfiles(profiles);

        System.out.println("===========启动环境=====" + flag);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .groupName("public")
                .select()
                .apis(RequestHandlerSelectors.basePackage(SwaggerConfig.SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置apiInfo信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短网址服务")
                .description("提供长域名转短域名服务，短域名转长域名服务")
                .version(SwaggerConfig.VERSION)
                .build();
    }
}
