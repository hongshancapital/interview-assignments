package com.sequoia.urllink;


import com.sequoia.urllink.constant.Const;
import com.sequoia.urllink.constant.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * 访问方式 ：
 * http://localhost:port/contextPath/swagger-ui.html
 * <p>
 * 例如：
 * http://localhost:8082/bak-report/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2ApiDoc {
  private final static String SWAGGER_DESC = "大数据API";
  private final static String SWAGGER_OAUTH = "oauth2";
  private final static String SWAGGER_PLATFORM = "大数据平台";
  private final static String SWAGGER_SCOPE = "BigData";
  private final static String SWAGGER_VERSION = "2.0";
  @Autowired
  private Environment env;

  @Bean
  public Docket buildDocket() {
    OAuth oAuth = new OAuth(SWAGGER_OAUTH, Arrays.asList(new AuthorizationScope(SWAGGER_SCOPE, SWAGGER_DESC)),
        Arrays.asList(new ImplicitGrant(new LoginEndpoint("http://fulu/api/connect/authorize"), " ")));

    SecurityReference securityReference = SecurityReference.builder().reference(SWAGGER_OAUTH)
        .scopes(new AuthorizationScope[]{new AuthorizationScope(SWAGGER_SCOPE, SWAGGER_DESC)}).build();

    SecurityContext securityContext =
        SecurityContext.builder().securityReferences(Arrays.asList(securityReference)).build();

    String envName = env.getProperty(Const.RUN_ENV);
    boolean dev = !(StringUtils.isBlank(envName) || Const.RUN_ENV_PRO.equals(envName.trim().toLowerCase()));

    return new Docket(DocumentationType.SWAGGER_2).enable(dev) //生产环境设置成false
        .securityContexts(Arrays.asList(securityContext)).securitySchemes(Arrays.asList(oAuth)).forCodeGeneration(false)
        .useDefaultResponseMessages(false).apiInfo(buildApiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage(Global.SWAGGER2_BASE_PACKAGE)).paths(PathSelectors.any()).build();
  }


  private ApiInfo buildApiInfo() {
    String packageName = com.sequoia.urllink.Swagger2ApiDoc.class.getPackage().getName();
    String[] names = packageName.split("\\.");
    String termsOfServiceUrl = StringUtils.capitalize(names[names.length - 1]);

    return new ApiInfoBuilder().title(SWAGGER_PLATFORM).description(SWAGGER_DESC).termsOfServiceUrl(termsOfServiceUrl)
        //                        .contact(new Contact("Swagger2使用样例", "http://10.0.0.60:10080/swagger/", ""))
        .version(SWAGGER_VERSION).build();
  }

}
