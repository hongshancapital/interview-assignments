package com.scdt.java.shortLink.component.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class UrlCacheConfig {
    @Value("${urlCacheConfig.initialCapacity}")
    private Integer initialCapacity;
    @Value("${urlCacheConfig.maximumSize}")
    private Integer maximumSize;
}
