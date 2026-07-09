package scdt.interview.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import scdt.interview.java.common.utils.LRUMapCache;

@Configuration
public class SystemConfig {
	@Value("${scdt.cache.capable.short}")
	private int initShortCapable;

	@Value("${scdt.cache.capable.long}")
	private int initLongCapable;

	@Bean
    public LRUMapCache<String, String> shortCacheMap() {
        return new LRUMapCache<>(initShortCapable);
	}
	@Bean
    public LRUMapCache<String, String> longMD5CacheMap() {
        return new LRUMapCache<>(initLongCapable);
	}
	
	
}
