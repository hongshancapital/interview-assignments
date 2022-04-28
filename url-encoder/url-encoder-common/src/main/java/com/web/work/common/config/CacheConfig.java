package com.web.work.common.config;

import com.web.work.common.util.LocalCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * the config of cache
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
@Configuration
public class CacheConfig {

    @Bean
    public LocalCache initLocalCache() {
        return new LocalCache(10L);
    }
}
