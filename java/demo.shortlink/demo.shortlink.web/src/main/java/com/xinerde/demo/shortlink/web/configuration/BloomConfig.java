package com.xinerde.demo.shortlink.web.configuration;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器配置
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
@Configuration
public class BloomConfig {
    private long insertion=10000000;
    private double fpp=0.0001;

    @Bean
    public BloomFilter<String> createBloomFilter(){
        return BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),insertion,fpp);
    }
}