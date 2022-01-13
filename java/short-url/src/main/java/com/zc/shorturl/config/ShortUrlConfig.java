package com.zc.shorturl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 短域名配置类
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "short", ignoreInvalidFields = true)
public class ShortUrlConfig {
    // 默认自定义域名，提供给短链接使用
    private String defaultCustomDomain;
}
