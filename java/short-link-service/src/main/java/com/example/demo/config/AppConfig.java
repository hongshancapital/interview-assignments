package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@Component
@ConfigurationProperties(prefix = "app.short-link")
public class AppConfig {

    /**
     * 缺省的生成短连接的域名，为空则取访问服务时的域名
     */
    private URI shortDomain;

    /**
     * 起始序列号
     */
    private long startSerialNumber = 3844;

    /**
     * 缓存短连接最大数量
     */
    private long cacheSize = 100_0000;

    /**
     * 缓存短连接过期时间，单位秒
     */
    private long cacheExpire = 2 * 60 * 60;

    public URI getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(URI shortDomain) {
        this.shortDomain = shortDomain;
    }

    public long getStartSerialNumber() {
        return startSerialNumber;
    }

    public void setStartSerialNumber(long startSerialNumber) {
        this.startSerialNumber = startSerialNumber;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    public long getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(long cacheExpire) {
        this.cacheExpire = cacheExpire;
    }
}
