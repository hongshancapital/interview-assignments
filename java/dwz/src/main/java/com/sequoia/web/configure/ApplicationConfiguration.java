package com.sequoia.web.configure;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.sequoia.web.mapper.UrlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);
    // 布隆过滤器预计插入大小, 按此计算bits使用量
    @Value("${filter.bloom.expected-insertions}")
    private long expectedInsertion;
    // 布隆过滤器冲突几率, 按此计算bits使用量
    @Value("${filter.bloom.fpp}")
    private double fpp;
    // 本地map策略类型
    @Value("${mapper.strategy}")
    private UrlMapper.Strategy strategy;
    // 是否清理过期键值对
    @Value("${mapper.enable-expire:true}")
    private Boolean enableExpire;
    // 过期时间（单位：日）
    @Value("${mapper.ttl-of-day:30}")
    private Long ttlOfDay;
    // 清理定期任务延时
    @Value("${mapper.initial-delay:0}")
    private Long initialDelay;
    // 清理定期任务间隔
    @Value("${mapper.period:1}")
    private Long period;
    // 清理定期任务时间单位
    @Value("${mapper.time-unit:DAYS}")
    private TimeUnit timeUnit;

    @Bean
    public BloomFilter<String> getFilter(){
        LOGGER.info("Creating BloomFilter with expectedInsertion of {} and fpp of {}",expectedInsertion,fpp);
        return BloomFilter.create(Funnels.stringFunnel(Charsets.US_ASCII), expectedInsertion, fpp);
    }
    @Bean
    public UrlMapper getUrlMapper() {
        LOGGER.info("Creating UrlMapper with strategy of {} and ttl of {} days. " +
                "enableExpire: {}, initialDelay: {}, period: {}， timeUnit: {}", strategy, ttlOfDay,enableExpire,
                initialDelay,
                period, timeUnit );
        return new UrlMapper(strategy,
                enableExpire,
                ttlOfDay * 1000L * 60 * 60 * 24,
                initialDelay,
                period, timeUnit);
    }

}
