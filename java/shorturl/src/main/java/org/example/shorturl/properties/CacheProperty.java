package org.example.shorturl.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bai
 * @date 2022/1/24 21:43
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheProperty {
    /** 过期时间 */
    private long timeout = 3 * 30 * 24 * 60 * 60 * 1000L;
    /** 容量 */
    private Integer capacity = 10000000;
}
