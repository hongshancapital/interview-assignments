package org.example.shorturl.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * url转换属性
 *
 * @author bai
 * @date 2022/3/19 21:10
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "url")
public class UrlTransformProperty {
    /** 前缀 */
    private String prefix;
    /** ssl */
    private Boolean ssl;
}
