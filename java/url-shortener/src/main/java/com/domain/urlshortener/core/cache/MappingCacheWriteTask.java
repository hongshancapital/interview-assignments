package com.domain.urlshortener.core.cache;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:23
 */
public class MappingCacheWriteTask implements Runnable {

    private MappingCache mappingCache;
    private MappingCacheEntry mappingCacheEntry;

    public MappingCacheWriteTask(MappingCache mappingCache, MappingCacheEntry mappingCacheEntry) {
        this.mappingCache = mappingCache;
        this.mappingCacheEntry =  mappingCacheEntry;
    }

    @Override
    public void run() {
        mappingCache.put(mappingCacheEntry.getKey(), mappingCacheEntry.getValue());
    }

}
