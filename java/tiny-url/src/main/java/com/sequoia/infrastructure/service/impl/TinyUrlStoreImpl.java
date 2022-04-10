package com.sequoia.infrastructure.service.impl;

import com.github.benmanes.caffeine.cache.*;
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

;

/**
 * TinyUrlGenerator: 长短链 存储类
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Slf4j
@Component("tinyUrlStore")
public class TinyUrlStoreImpl implements ITinyUrlStore {

    @Resource(name = "codeGenerator")
    private ICodeGenerator codeGenerator;

    @Value("${generate.retry.count.max}")
    private Integer generateRetryMaxCount = 5;

    /**
     * 基于 key 级别的锁
     */
    private static final Striped<Lock> stripedKeyLock = Striped.lock(512);
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
            Caffeine.newBuilder()
                    .expireAfterWrite(30, TimeUnit.SECONDS)
                    .initialCapacity(10240)
                    .maximumSize(10240)
                    .build(key -> new ProducerPromise<>(Thread.currentThread()));

    /**
     * caffeine 实现的基于 weight 的内存
     */
    private static final Cache<String, String> TINY_ORIGIN_STORE =
            Caffeine.newBuilder()
                    .maximumWeight(TINY_STORE_MAX_WEIGHT)
                    .weigher((Weigher<String, String>)(k, v) -> (k.length() + v.length()) * 2)
                    .build();

    /**
     * 保存 短码 和 原始长链接的 映射关系
     * @param tinyCode 短码
     * @param originUrl 原始长链接
     * @return
     */
    private boolean putOriginUrl(String tinyCode, String originUrl) {
        if (null == tinyCode) { return false; }
        TINY_ORIGIN_STORE.put(tinyCode, originUrl);
        return true;
    }

    /**
     * 获取 短码 对应的 锁
     * @param tinyCode 短码
     * @return key锁
     */
    public Lock getLock(String tinyCode) {
        return stripedKeyLock.get(tinyCode);
    }

    /**
     * 根据长链接 生成相应 短码
     * @param originUrl 原始长链接
     * @return
     */
    private String tinyCode(String originUrl) {
        return codeGenerator.generateTinyCode(originUrl);
    }

    /**
     * 保存 tinyCode 和 originUrl 映射关系
     * - 需要加锁进行保存操作，加锁成功后需判断存储中是否已生成 相应的长短码对应关系
     * - 锁可以是：
     *  a、内存级 key 锁 （此处使用的 guava 的 Striped 锁）
     *  b、分布式锁
     * @param tinyCode 短码
     * @param originUrl 相应的长链接
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
                return this.putOriginUrl(tinyCode, originUrl);
            } else {
                return StringUtils.equals(storeOriginUrl, originUrl);
            }
        } catch (InterruptedException e) {
            log.error("操作中断异常", e);
            throw new LockException("操作中断异常");
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

    /**
     * 根据原生长链接生成相应短码，如果发生冲突，则调整原生长链接重新生成短码，直到重试次数用完
     * @param originUrl 原生长链接
     * @return 对应的短码
     *         or 达到最大重试次数，仍不成功，直接抛出 TinyCodeException
     */
    private String generateTinyCode(String originUrl) {
        // 根据originUrl生成对应的短码
        String tinyCode = this.tinyCode(originUrl);

        String storeOriginUrl = this.getOriginUrl(tinyCode);
        if (StringUtils.equals(originUrl, storeOriginUrl)) {
            return tinyCode;
        }

        if (null == storeOriginUrl) {
            if (saveTinyOriginCodeMapping(tinyCode, originUrl)) {
                return tinyCode;
            }
        }

        for (Integer i = 1; i <= generateRetryMaxCount; i++) {
            // 如果发生冲突，则在原生长链接上append 字符串，重新生成短码，直到生成不重复的短码 或 达到最大重试次数
            String originUrlNew = originUrl + i;
            tinyCode = this.tinyCode(originUrlNew);

            if (saveTinyOriginCodeMapping(tinyCode, originUrl)) {
                return tinyCode;
            }
        }

        log.error("hash冲突，已连续生成 {} 次", generateRetryMaxCount);
        throw new TinyCodeException("hash冲突，已连续生成 " + generateRetryMaxCount + " 次");
    }

    /**
     * 根据原始长链接生成相应的 短码
     * @param originUrl 长链接
     * @return 附带短码信息的 Future
     */
    @Override
    public CompletableFuture<String> getTinyUrlFuture(String originUrl) {
        if (null == originUrl) { return CompletableFuture.completedFuture(null); }

        final ProducerPromise<String> tinyCodePromise = TINY_CODE_CACHE.get(originUrl);

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

    /**
     * 从 长短链接 数据源中获取数据
     * @param tinyCode 短码
     * @return 原始长链接
     */
    @Override
    public String getOriginUrl(String tinyCode) {
        if (null == tinyCode) { return null; }
        return TINY_ORIGIN_STORE.getIfPresent(tinyCode);
    }

}
