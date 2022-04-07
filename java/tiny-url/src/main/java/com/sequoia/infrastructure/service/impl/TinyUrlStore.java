package com.sequoia.infrastructure.service.impl;

import com.google.common.cache.*;
import com.google.common.util.concurrent.Striped;
import com.sequoia.infrastructure.common.ProducerPromise;
import com.sequoia.infrastructure.common.exception.LockException;
import com.sequoia.infrastructure.common.exception.TinyCodeException;
import com.sequoia.service.ICodeGenerator;
import com.sequoia.service.ITinyUrlStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Descript:
 * File: com.sequoia.infrastructure.service.impl.TinyUrlStore
 * Author: daishengkai
 * Date: 2022/3/30 20:41
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@Component
public class TinyUrlStore implements ITinyUrlStore {

    @Resource
    private ICodeGenerator codeGenerator;

    @Value("${generate.count.max}")
    private Integer generateMaxCount = 5;

    /**
     * 基于 key 级别的锁
     */
    private static final Striped<Lock> stripedKeyLock = Striped.lock(255);
    private static final int LOCK_TIMEOUT = 1000;
    private static final TimeUnit LOCK_TIMEOUT_TIMEUNIT = TimeUnit.MILLISECONDS;

    private static final int CPU_CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private static final long TINY_STORE_MAX_WEIGHT = 1024_000_000L;

    /**
     * 使用 guava loadingCache 缓存 future 方式，来减少客户端并发
     * 作用：
     * 1、本地缓存，30s过期一次，抵抗刷子流量
     * 2、并发请求进来，只有第一个请求会做 生成短链接动作
     */
    private LoadingCache<String, ProducerPromise<String>> TINY_CODE_CACHE =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(30, TimeUnit.SECONDS)
                    .initialCapacity(10240)
                    .concurrencyLevel(CPU_CORE_COUNT)
                    .maximumSize(10240)
                    .build(new CacheLoader<String, ProducerPromise<String>>() {
                        @Override
                        public ProducerPromise<String> load(String key) throws Exception {
                            return new ProducerPromise<>(Thread.currentThread());
                        }
                    });

    /**
     * guava 实现的基于 weight 的内存
     */
    private static final Cache<String, String> TINY_ORIGIN_STORE =
            CacheBuilder.newBuilder()
                    .concurrencyLevel(CPU_CORE_COUNT)
                    .maximumWeight(TINY_STORE_MAX_WEIGHT)
                    .weigher((Weigher<String, String>)(k, v) -> (k.length() + v.length()) * 2)
                    .build();

    private String getOriginUrl(String tinyCode) {
        if (null == tinyCode) { return null; }
        return TINY_ORIGIN_STORE.getIfPresent(tinyCode);
    }

    private boolean putOriginUrl(String tinyCode, String originUrl) {
        if (null == tinyCode) { return false; }
        TINY_ORIGIN_STORE.put(tinyCode, originUrl);
        return true;
    }

    public Lock getLock(String tinyCode) {
        return stripedKeyLock.get(tinyCode);
    }

    /**
     * 保存 tinyCode 和 originUrl 映射关系
     * @param tinyCode
     * @param originUrl
     * @return
     */
    private boolean saveTinyOriginCodeMapping(String tinyCode, String originUrl) {
        Lock lock = getLock(tinyCode);

        try {
            if (!lock.tryLock(LOCK_TIMEOUT, LOCK_TIMEOUT_TIMEUNIT)) {
                log.error("在 {} {} 内加锁失败", LOCK_TIMEOUT, LOCK_TIMEOUT_TIMEUNIT);
                return false;
            }

            String storeOriginUrl = this.getOriginUrl(tinyCode);
            if (null == storeOriginUrl) {
                return putOriginUrl(tinyCode, originUrl);
            } else {
                return StringUtils.equals(storeOriginUrl, originUrl);
            }
        } catch (InterruptedException e) {
            throw new LockException("加锁异常");
        } finally {
            if (null != lock) {
                lock.unlock();
            }
        }

    }

    /**
     * 生成短链接
     * @param originUrl
     * @return
     */
    private CompletableFuture<String> generateTinyCodeFuture(String originUrl) {
        return CompletableFuture.completedFuture(originUrl).thenApply(this::generateTinyCode);
    }

    private String tinyCode(String originUrl) {
        return codeGenerator.generateTinyCode(originUrl);
    }

    private String generateTinyCode(String originUrl) {
        // 根据originUrl生成对应的短码
        String tinyCode = this.tinyCode(originUrl);

        String storeOriginUrl = this.getOriginUrl(tinyCode);
        if (null == storeOriginUrl) {
            if (saveTinyOriginCodeMapping(tinyCode, originUrl)) {
                return tinyCode;
            }
        }

        if (StringUtils.equals(originUrl, storeOriginUrl)) {
            return tinyCode;
        }

        for (Integer i = 1; i <= generateMaxCount; i++) {
            tinyCode = this.tinyCode(originUrl);
            if (saveTinyOriginCodeMapping(tinyCode, originUrl)) {
                return tinyCode;
            }
        }

        log.error("hash冲突，已连续生成 {} 次", generateMaxCount);
        throw new TinyCodeException("hash冲突，已连续生成 " + generateMaxCount + " 次");
    }

    @Override
    public CompletableFuture<String> getTinyUrlFuture(String originUrl) {
        if (null == originUrl) { return CompletableFuture.completedFuture(null); }

        final ProducerPromise<String> tinyCodePromise = TINY_CODE_CACHE.getUnchecked(originUrl);

        if (tinyCodePromise.shouldProduce()) {
            generateTinyCodeFuture(originUrl).whenComplete((result, e) -> {
                if (null != e) {
                    tinyCodePromise.completeExceptionally(e);
                    TINY_CODE_CACHE.invalidate(originUrl);
                } else {
                    tinyCodePromise.complete(result);
                }
            });
        }

        return tinyCodePromise.promise();
    }

    @Override
    public CompletableFuture<String> getOriginUrlFuture(String tinyCode) {
        return CompletableFuture.completedFuture(this.getOriginUrl(tinyCode));
    }

}
