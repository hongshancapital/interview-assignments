package com.bolord.shorturl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短链接配置
 *
 * @author alex
 */
@ConfigurationProperties(prefix = "short-url", ignoreUnknownFields = true)
public class ShortUrlProperties {

    /**
     * 短链接前缀
     */
    private String prefix = "http://localhost:8080/";

    /**
     * 当短链接未匹配到时，默认重定向的 URL
     */
    private String defaultRedirectUrl = "/error/404";

    /**
     * 最大缓存数量，防止内存溢出
     */
    private long maxCacheSize = 100000L;

    public ShortUrlProperties() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDefaultRedirectUrl() {
        return defaultRedirectUrl;
    }

    public void setDefaultRedirectUrl(String defaultRedirectUrl) {
        this.defaultRedirectUrl = defaultRedirectUrl;
    }

    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

}
