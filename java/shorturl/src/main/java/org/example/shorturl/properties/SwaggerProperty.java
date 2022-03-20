package org.example.shorturl.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shorturl.constants.AppConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger配置参数对象。
 *
 * @author bai
 * @date 2021-01-14
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperty {
    /**
     * ApiInfo中的版本信息。
     **/
    private String version = AppConstant.APP_VERSION;
    /**
     * Swagger解析的基础包路径。
     **/
    private String basePackage = AppConstant.BASE_PACKAGES;
    /**
     * ApiInfo中的标题。
     **/
    private String title = "接口文档";
    /**
     * ApiInfo中的描述信息。
     **/
    private String description = "接口文档";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * 联系人信息
     */
    private Contact contact = new Contact();
    
    /**
     * 联系人信息,这次就简单写一下自己的博客跟邮箱吧
     *
     * @author bai
     * @date 2022/03/19
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "bai";
        /**
         * 联系人url
         **/
        private String url = "https://halo.baicloud.top";
        /**
         * 联系人email
         **/
        private String email = "bai31415926@126.com";
    }
}
