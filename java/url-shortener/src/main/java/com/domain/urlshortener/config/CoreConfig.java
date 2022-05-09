package com.domain.urlshortener.config;

import com.domain.urlshortener.core.ShortenManager;
import com.domain.urlshortener.core.cache.MappingCache;
import com.domain.urlshortener.core.cache.MappingCacheWriteExecutor;
import com.domain.urlshortener.core.cache.MemoryMappingCache;
import com.domain.urlshortener.core.sequence.MemorySequenceGenerator;
import com.domain.urlshortener.core.sequence.SequenceGenerator;
import com.domain.urlshortener.core.store.MappingStore;
import com.domain.urlshortener.core.store.MemoryMappingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rocky.hu
 * @date: 2022/4/5 23:08
 */
@Configuration
public class CoreConfig {

    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new MemorySequenceGenerator();
    }

    @Bean
    public MappingStore mappingStore(ConfigProperties configProperties) {
        return new MemoryMappingStore(configProperties.getMaximumSize());
    }

    @Bean
    public MappingCache mappingCache(ConfigProperties configProperties) {
        return new MemoryMappingCache(configProperties.getCacheSize(), configProperties.getCacheTtl());
    }

    @Bean
    public MappingCacheWriteExecutor mappingCacheWriteExecutor(ConfigProperties configProperties) {
        return new MappingCacheWriteExecutor(configProperties.getCacheSize().intValue());
    }

    @Bean
    public ShortenManager shortenManager(SequenceGenerator sequenceGenerator, MappingStore mappingStore,
                                         MappingCache mappingCache, MappingCacheWriteExecutor mappingCacheWriteExecutor) {
        return new ShortenManager(sequenceGenerator, mappingStore, mappingCache, mappingCacheWriteExecutor);
    }

}
