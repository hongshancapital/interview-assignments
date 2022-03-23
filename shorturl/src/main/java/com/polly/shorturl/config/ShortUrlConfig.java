package com.polly.shorturl.config;

import com.polly.shorturl.cache.UrlCache;
import com.polly.shorturl.tools.ShortUrlHandler;
import com.polly.shorturl.tools.SnowflakeIdWorker;
import com.polly.shorturl.tools.UrlTransfer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author polly
 * @date 2022.03.20 23:59:32
 */
@Configuration(proxyBeanMethods = false)
public class ShortUrlConfig {

    @Bean
    public UrlCache urlCache() {
        return new UrlCache();
    }

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(0, 0);
    }

    @Bean
    public UrlTransfer urlTransfer() {
        return new UrlTransfer();
    }

    @Bean
    public ShortUrlHandler shortUrlHandler(SnowflakeIdWorker worker, UrlTransfer transfer, UrlCache cache) {
        return new ShortUrlHandler(worker, transfer, cache);
    }
}
