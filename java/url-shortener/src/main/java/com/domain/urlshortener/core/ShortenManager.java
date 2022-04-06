package com.domain.urlshortener.core;

import com.domain.urlshortener.core.cache.MappingCache;
import com.domain.urlshortener.core.cache.MappingCacheEntry;
import com.domain.urlshortener.core.cache.MappingCacheWriteExecutor;
import com.domain.urlshortener.core.cache.MappingCacheWriteTask;
import com.domain.urlshortener.core.codec.Base62;
import com.domain.urlshortener.core.sequence.SequenceGenerator;
import com.domain.urlshortener.core.store.MappingStore;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 1:24
 */
public class ShortenManager {

    private SequenceGenerator sequenceGenerator;
    private MappingStore mappingStore;
    private MappingCache mappingCache;
    private MappingCacheWriteExecutor mappingCacheWriteExecutor;

    public ShortenManager(SequenceGenerator sequenceGenerator, MappingStore mappingStore,
                          MappingCache mappingCache, MappingCacheWriteExecutor mappingCacheWriteExecutor) {
        this.sequenceGenerator = sequenceGenerator;
        this.mappingStore = mappingStore;
        this.mappingCache = mappingCache;
        this.mappingCacheWriteExecutor = mappingCacheWriteExecutor;
    }

    public String shorten(String url) {
        String cacheValue = mappingCache.get(url);
        if (cacheValue != null) {
            return cacheValue;
        }

        String alias = Base62.encode(sequenceGenerator.generateSequenceNo());
        mappingStore.put(alias, url);

        MappingCacheEntry mappingCacheEntry = new MappingCacheEntry(url, alias);
        MappingCacheWriteTask mappingCacheWriteTask = new MappingCacheWriteTask(mappingCache, mappingCacheEntry);
        mappingCacheWriteExecutor.submit(mappingCacheWriteTask);

        return alias;
    }

    public String getUrl(String alias) {
        return mappingStore.get(alias);
    }

}
