package com.yilong.shorturl.bootstrap.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("swagger")
@Getter
@Setter
public class SwaggerProperties {

    private Boolean enable;
    private String applicationName;
    private String applicationVersion;
    private String applicationDescription;
    private String tryHost;
}

