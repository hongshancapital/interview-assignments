package com.url.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * 布隆过滤器配置
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@Slf4j
@Configuration
public class UrlBloomConfig {

    @Value("${url-bloom-filter.insertion}")
    private long insertion;

    @Value("${url-bloom-filter.fpp}")
    private double fpp;

    @Bean
    public BloomFilter<String> createBloomFilter(){
        return BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),insertion,fpp);
    }
}
