package com.hongshan.work.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hongshan.work.mapper.HashMapUrlMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    // 布隆过滤器预计插入大小, 按此计算bits使用量
    @Value("${filter.bloom.expected-insertions}")
    private long expectedInsertion;
    // 布隆过滤器
    @Value("${filter.bloom.fpp}")
    private double fpp;
    // 是否清理过期键值对
    @Value("${map.enable-expire:true}")
    private Boolean enableExpire;
    // 过期时间
    @Value("${map.ttl-of-day}")
    private Long ttlOfDay;
    // 清理定期任务延时
    @Value("${map.initial-delay:0}")
    private Long initialDelay;
    // 清理定期任务间隔
    @Value("${map.period:1}")
    private Long period;
    // 清理定期任务时间单位
    @Value("${map.time-unit:DAYS}")
    private TimeUnit timeUnit;

    @Bean
    public BloomFilter<String> getFilter(){
        log.info("Creating BloomFilter with expectedInsertion of {} and fpp of {}",expectedInsertion,fpp);
        return BloomFilter.create(Funnels.stringFunnel(Charsets.US_ASCII), expectedInsertion, fpp);
    }
    @Bean
    public HashMapUrlMap getUrlMapper() {
        log.info("Creating ConcurrentHashMap ttl of {} days. " +
                "enableExpire: {}, initialDelay: {}, period: {}， timeUnit: {}", ttlOfDay,enableExpire,
                initialDelay,
                period, timeUnit );
        return new HashMapUrlMap(
                enableExpire,
                ttlOfDay * 1000L * 60 * 60 * 24,
                initialDelay,
                period, timeUnit);
    }

}