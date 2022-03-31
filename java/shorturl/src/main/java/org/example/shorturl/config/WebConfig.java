package org.example.shorturl.config;

import org.example.shorturl.properties.CacheProperty;
import org.example.shorturl.properties.UrlTransformProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网络配置
 *
 * @author bai
 * @date 2021/12/9 21:28
 */
@Configuration
@EnableConfigurationProperties({UrlTransformProperty.class, CacheProperty.class})
public class WebConfig {

}
