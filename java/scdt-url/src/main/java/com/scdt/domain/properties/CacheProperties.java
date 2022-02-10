package com.scdt.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Data
@ConfigurationProperties(prefix = "jvm.cache")
public class CacheProperties {
    /**
     * 过期时间，单位：秒
     */
    private int ttl;

    /**
     * 初始容量大小
     */
    private int initSize;

    /**
     * 最大容量多少
     */
    private int maxSize;

}
