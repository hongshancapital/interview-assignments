package com.domain.urlshortener.core.cache;

import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:28
 */
public class MappingCacheWriteTaskTest {

    @Test
    public void test() {
        MappingCache mappingCache = new MemoryMappingCache(10000L, 30);
        MappingCacheEntry mappingCacheEntry = new MappingCacheEntry("a", "b");
        MappingCacheWriteTask mappingCacheWriteTask = new MappingCacheWriteTask(mappingCache, mappingCacheEntry);
        mappingCacheWriteTask.run();
    }

}
