package com.example.app.common.utils;

import com.example.app.AppApplicationTests;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author voidm
 * @date 2021/9/19
 */
public class LRUCacheTest extends AppApplicationTests {

    public static final int len = 10;
    public static final LRUCache <String,String> lruCache = new LRUCache(len);
    @Test
    public void generateShortUrl() {

        log.info(String.format("测试 LRUCache 最大长度 >> [%s]", 10));
        for (int i = 0; i < 100; i++) {
            lruCache.put(i+"",i+"");
        }
        log.info(String.format("测试 LRUCache 插入100条数据后长度 << [%s]", lruCache.size()));
        Assert.assertEquals(len, lruCache.size());
    }
}