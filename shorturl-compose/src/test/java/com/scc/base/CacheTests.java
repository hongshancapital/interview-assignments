package com.scc.base;

import com.scc.base.cache.LfuCache;
import com.scc.base.cache.LruCache;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author renyunyi
 * @date 2022/4/26 1:56 PM
 * @description LRU and LFU test
 **/
public class CacheTests {
    @Test
    public void testLRU(){
        LruCache<String, String> lruCache = new LruCache<String, String>(10);
        lruCache.put("hello", "world");
        assert StringUtils.equals("world", lruCache.get("hello"));
    }

    @Test
    public void testLFU(){
        LfuCache<String, String> lfuCache = new LfuCache<String, String>(10);
        lfuCache.put("hello", "world");
        assert StringUtils.equals("world", lfuCache.get("hello"));
    }

    @Test
    public void testLFUCacheNode(){
        LfuCache.CacheNode<String, String> cacheNode1 = new LfuCache.CacheNode<>("hello", "world");
        LfuCache.CacheNode<String, String> cacheNode2 = new LfuCache.CacheNode<>("hello", "world");
        cacheNode2.setValue("world");
        System.out.println(cacheNode1.hashCode());
        assert cacheNode1.equals(cacheNode2);
    }
}
