package com.wenchao.jacoco.demo.storage;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 长短链接映射存储 内存实现
 * 使用双向Map 可以 key/value 互查
 * @author Wenchao Gong
 * @date 2021-12-15
 */
public class InMemoryStorage implements IStorage {

    private BiMap<Long, String> storage = HashBiMap.create();
    private AtomicLong index = new AtomicLong();

    @Override
    public void put(String longUrl, Long shortUrl) {
        //向HashBiMap中插入时需要保证线程安全，否则可能会使链表变成循环链表，导致取数据是死循环
        //见：https://my.oschina.net/u/3874284/blog/4307973
        synchronized (storage) {
            storage.put(shortUrl, longUrl);
        }
    }

    @Override
    public String getLongUrl(Long shortUrl) {
        return storage.get(shortUrl);
    }

    @Override
    public Long getShortUrl(String longUrl) {
        return storage.inverse().get(longUrl);
    }

    @Override
    public Long nextVal() {
        return index.incrementAndGet();
    }
}
