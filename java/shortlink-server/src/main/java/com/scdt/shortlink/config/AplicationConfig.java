package com.scdt.shortlink.config;

/**
 * @author xbhong
 * @date 2022/4/17 16:37
 */

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.scdt.shortlink.util.sequence.AtomicLongSequenceGenerator;
import com.scdt.shortlink.util.sequence.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class AplicationConfig {

    // 布隆过滤器，预估数据量
    @Value("${bloom-filter.insertions}")
    private long insertion;

    // 布隆过滤器，判重错误率
    @Value("${bloom-filter.fpp}")
    private double fpp;

    // caffeine初始的缓存空间大小
    @Value("${caffeine.initial-capacity}")
    private int initialCapacity;

    // caffeine缓存的最大条数
    @Value("${caffeine.maximum-size}")
    private long maximumSize;

    // caffeine最后一次写入后经过固定时间过期，单位为分
    @Value("${caffeine.expire-after-write}")
    private long expireAfterWrite;

    @Bean
    public BloomFilter<String> getFilter() {
        log.info("Creating BloomFilter with insertion of {} and fpp of {}", insertion, fpp);
        return BloomFilter.create(Funnels.stringFunnel(Charsets.US_ASCII), insertion, fpp);
    }

    /**
     * Caffeine配置说明：
     * initialCapacity=[integer]: 初始的缓存空间大小
     * maximumSize=[long]: 缓存的最大条数
     * maximumWeight=[long]: 缓存的最大权重
     * expireAfterAccess=[duration]: 最后一次写入或访问后经过固定时间过期
     * expireAfterWrite=[duration]: 最后一次写入后经过固定时间过期
     * refreshAfterWrite=[duration]: 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
     * weakKeys: 打开key的弱引用
     * weakValues：打开value的弱引用
     * softValues：打开value的软引用
     * recordStats：开发统计功能
     * 注意：
     * expireAfterWrite和expireAfterAccess同事存在时，以expireAfterWrite为准。
     * maximumSize和maximumWeight不可以同时使用
     * weakValues和softValues不可以同时使用
     */
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(initialCapacity)
                // 缓存的最大条数
                .maximumSize(maximumSize).build();
    }

    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new AtomicLongSequenceGenerator();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        // 修改内置的 tomcat 容器配置
        TomcatServletWebServerFactory tomcatServlet = new TomcatServletWebServerFactory();
        tomcatServlet.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "XXX"));
        return tomcatServlet;
    }
}
