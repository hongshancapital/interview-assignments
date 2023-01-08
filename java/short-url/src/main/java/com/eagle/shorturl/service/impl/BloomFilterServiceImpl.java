package com.eagle.shorturl.service.impl;

import com.eagle.shorturl.exception.BussinessException;
import com.eagle.shorturl.result.ResultCodeEnum;
import com.eagle.shorturl.service.BloomFilterService;
import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author eagle
 * @description
 */
@Slf4j
@Service
@Validated
public class BloomFilterServiceImpl implements BloomFilterService {

    /**
     * 布隆过滤器数量，此处设置受限于内存大小
     */
    private static final int CAPACITY = 2;
    private final List<BloomFilter<String>> pool = Lists.newCopyOnWriteArrayList();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static final int LOCK_TIMEOUT_MILLISECONDS = 500;

    @PostConstruct
    public void init() {
        for (int i = 0; i < CAPACITY; i++) {
            /**
             * 单个过滤器：类型:String 容量:1亿 误判率:0.0001
             */
            pool.add(BloomFilter.create((Funnel<String>) (s, primitiveSink) -> primitiveSink.putString(s, StandardCharsets.UTF_8), 100_000_000L, 0.0001));
        }
    }

    @Override
    public boolean mightContain(@NotBlank String key) {
        int index = key.hashCode() % CAPACITY;
        try {
            boolean acquired = readLock.tryLock(LOCK_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
            if (!acquired) {
                throw new BussinessException(ResultCodeEnum.ERROR_BLOOM_FILTER, MessageFormat.format("Try to acquire lock for key:{0} failed", key));
            }
            boolean result = pool.get(index).mightContain(key);
            log.info("bloomFilter contain key:{} result:{}", key, result);
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            readLock.unlock();
        }
        throw new BussinessException(ResultCodeEnum.ERROR_BLOOM_FILTER);
    }

    @Override
    public boolean put(@NotBlank String key) {
        int index = key.hashCode() % CAPACITY;
        try {
            boolean acquired = writeLock.tryLock(LOCK_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
            if (!acquired) {
                throw new BussinessException(ResultCodeEnum.ERROR_BLOOM_FILTER, MessageFormat.format("Try to acquire lock for key:{0} failed", key));
            }
            boolean result = pool.get(index).put(key);
            log.info("bloomFilter put key:{} result:{}", key, result);
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
        throw new BussinessException(ResultCodeEnum.ERROR_BLOOM_FILTER);
    }

}
