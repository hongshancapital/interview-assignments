package com.sequoia.shorturl.config;

import com.sequoia.shorturl.web.repository.UrlConvertorMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yanggj
 * @Description: urlMap配置类
 * @Date: 2022/1/5 22:14
 * @Version: 1.0.0
 */
@Configuration
public class UrlMappingConfiguration {

    @Value("${url-mapping.ttl:1}")
    private long ttl;
    @Value("${url-mapping.period:1}")
    private long period;
    @Value("${url-mapping.timeUnit:DAYS}")
    private TimeUnit timeUnit;

    @Bean
    public UrlConvertorMapping getUrlConvertorMapping() {
        return new UrlConvertorMapping(ttl, period, timeUnit);
    }
}
