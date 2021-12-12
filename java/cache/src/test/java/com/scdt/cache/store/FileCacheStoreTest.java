package com.scdt.cache.store;

import com.scdt.cache.CacheEntry;
import com.scdt.cache.lru.LRUCacheEntry;
import org.junit.Assert;
import org.junit.Test;

public class FileCacheStoreTest {

    @Test
    public void test() throws Exception {
        FileCacheStore fileCacheStore = new FileCacheStore();
        CacheEntry cacheEntry = new LRUCacheEntry(1, 1);
        fileCacheStore.store(cacheEntry);

        CacheEntry entry = fileCacheStore.load(cacheEntry.getKey());
        Assert.assertEquals(entry.getValue(), cacheEntry.getValue());

        entry = fileCacheStore.remove(cacheEntry.getKey());
        Assert.assertEquals(entry.getValue(), cacheEntry.getValue());

        entry = fileCacheStore.load(cacheEntry.getKey());
        Assert.assertNull(entry);

    }

}