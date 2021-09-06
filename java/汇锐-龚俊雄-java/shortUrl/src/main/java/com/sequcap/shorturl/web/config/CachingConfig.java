package com.sequcap.shorturl.web.config;

import com.sequcap.shorturl.service.impl.ShortUrlConvertServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class CachingConfig {

    Logger log = LoggerFactory.getLogger(ShortUrlConvertServiceImpl.class);

    public static final String URL_MODEL = "UrlConvert:UrlModel";
    public static final String DATE_FMT_Y_M_D_HMSS = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(URL_MODEL);

        return cacheManager;
    }

    /**
     * 每指定时间刷新一下缓存
     */
    @CacheEvict(allEntries = true, value = {URL_MODEL})
    @Scheduled(fixedDelay = 10 * 60 * 1000 ,  initialDelay = 500)
    public void reportCacheEvict() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FMT_Y_M_D_HMSS);
        log.info("Flush Cache " + dateFormat.format(new Date()));
    }
}
