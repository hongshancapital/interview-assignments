package com.snail.shorturlservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "surl")
public class ApplicationProperties {
    private int cacheTime = 60;
    private TimeUnit cacheTimeUnit;
    private String domain;

    public int getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    public TimeUnit getCacheTimeUnit() {
        return cacheTimeUnit;
    }

    public void setCacheTimeUnit(TimeUnit cacheTimeUnit) {
        this.cacheTimeUnit = cacheTimeUnit;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
