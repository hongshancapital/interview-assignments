package com.mortimer.shortenurl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "shortenurl")
@Getter
@Setter
public class ConfigProperties {
    private long simpleGeneratorInitValue;

    private long shortUrlLongUrlCacheMaxSize;

    private long shortUrlTooLongUrlCacheMaxSize;

    private long longUrlShortUrlCacheMaxSize;

    private int longUrlShortUrlCacheExpireTime;

    private String domain;
}
