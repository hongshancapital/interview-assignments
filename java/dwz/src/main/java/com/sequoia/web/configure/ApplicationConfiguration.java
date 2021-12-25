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
    @Value("${filter.bloom.expected-insertions}")
    private long expectedInsertion;
    @Value("${filter.bloom.fpp}")
    private double fpp;
    @Value("${mapper.strategy}")
    private UrlMapper.Strategy strategy;
    @Value("${mapper.enable-expire:true}")
    private Boolean enableExpire;
    @Value("${mapper.ttl-of-day:30}")
    private Long ttlOfDay;
    @Value("${mapper.initial-delay:0}")
    private Long initialDelay;
    @Value("${mapper.period:1}")
    private Long period;
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
