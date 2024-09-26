package net.sky.demo.url.mapping.service;


import net.sky.demo.url.mapping.store.Long2ShortMappingStore;
import net.sky.demo.url.mapping.store.Short2LongMappingStore;
import net.sky.demo.url.mapping.util.Long2StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 长短域名映射服务
 */
@Service
public class UrlMappingService {

    @Resource
    private Long2ShortMappingStore long2ShortMappingStore;
    @Resource
    private Short2LongMappingStore short2LongMappingStore;


    //读写锁，实现线程安全
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();


    /**
     * 生成短域名
     *
     * @param url 长域名
     * @return
     */
    public String generateShortUrl(String url) {
        writeLock.lock();
        try {
            //查询是否存在
            Long result = long2ShortMappingStore.queryBySourceUrl(url);
            if (result == null) {
                //不存在则生成
                result = short2LongMappingStore.generateNumber(url);
                long2ShortMappingStore.insertNewMapping(url, result);
            }
            return Long2StringUtil.long2String(result);
        } finally {
            writeLock.unlock();
        }

    }


    /**
     * 基于短域名查询长域名
     *
     * @param shortUrl
     * @return
     */
    public String querySourceUrl(String shortUrl) {
        readLock.lock();
        try {
            long shortNumber = Long2StringUtil.string2long(shortUrl);
            return short2LongMappingStore.getSourceUrlByNumber(shortNumber);
        } finally {
            readLock.unlock();
        }

    }

    public Long2ShortMappingStore getLong2ShortMappingStore() {
        return long2ShortMappingStore;
    }

    public void setLong2ShortMappingStore(Long2ShortMappingStore long2ShortMappingStore) {
        this.long2ShortMappingStore = long2ShortMappingStore;
    }

    public Short2LongMappingStore getShort2LongMappingStore() {
        return short2LongMappingStore;
    }

    public void setShort2LongMappingStore(Short2LongMappingStore short2LongMappingStore) {
        this.short2LongMappingStore = short2LongMappingStore;
    }
}
