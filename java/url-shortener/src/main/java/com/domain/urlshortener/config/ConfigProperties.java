package com.domain.urlshortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: rocky.hu
 * @date: 2022/4/2 0:02
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "shortener")
public class ConfigProperties {

    private String domain;
    private Long maximumSize;
    private Long cacheSize;
    private Integer cacheTtl;

}
