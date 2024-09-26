package com.scdt.china.shortdomain.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.scdt.china.shortdomain.service.DomainService;
import com.scdt.china.shortdomain.utils.ConversionUtil;
import com.scdt.china.shortdomain.utils.Result;
import com.scdt.china.shortdomain.utils.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: CZ
 * @Date: 2022/1/22 12:18
 * @Description:
 */
@Service
@Slf4j
public class DomainServiceImpl implements DomainService {
    private static AtomicLong autoIncrementId;
    /**
     * 存储全量数据，id对应长链接
     */
    private static Cache<Long, String> idToLongDomainCache;
    /**
     * 存储热点数据，长链接对应短链接
     */
    private static Cache<String, String> hotCache;

    @Value("${short.domain.init.id}")
    private Integer shortDomainInitId;
    @Value("${short.domain.max.length}")
    private Integer shortDomainMaxLength;

    @Value("${short.domain.total.max.size}")
    private Long shortDomainDayMaxSize;
    @Value("${short.domain.hot.max.size}")
    private Long shortDomainHotMaxSize;

    @PostConstruct
    public void initService() {
        autoIncrementId = new AtomicLong(shortDomainInitId);

        idToLongDomainCache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置缓存容器的初始容量为10
                .initialCapacity(10000)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(shortDomainDayMaxSize)
                //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
                .recordStats()
                //设置写缓存后n秒钟过期
                .expireAfterWrite(1, TimeUnit.DAYS)
                //设置缓存的移除通知
                .removalListener(notification -> {
                    log.info("idToLongCache key={}, value={}, 被移除,原因:{}", notification.getKey(), notification.getValue(), notification.getCause());
                }).build();

        hotCache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置缓存容器的初始容量为10
                .initialCapacity(100)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(shortDomainHotMaxSize)
                //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
                .recordStats()
                //设置写缓存后n秒钟过期
                .expireAfterWrite(5, TimeUnit.MINUTES)
                //设置缓存的移除通知
                .removalListener(notification -> {
                    log.debug("hotCache key={}, value={}, 被移除,原因:{}", notification.getKey(), notification.getValue(), notification.getCause());
                }).build();

    }

    /**
     * 1，访问热点数据缓冲中是否存在此长域名，如果存在直接返回
     * 2，
     *
     * @param longDomain 长链接域名参数
     * @return 返回短域名
     */
    @Override
    public Result<String> getShortDomain(String longDomain) {
        String ifPresent = hotCache.getIfPresent(longDomain);
        if (StringUtils.isNotBlank(ifPresent)) {
            return Result.success(ifPresent);
        }

        long newId = autoIncrementId.incrementAndGet();
        String shortDomain = ConversionUtil.encode10To62(newId);
        if (shortDomain.length() > shortDomainMaxLength) {
            log.error("shortDomain={}, newId={}, message={}", shortDomain, newId, ReturnCode.OUT_OF_RANGE_ERROR.getMessage());
            return Result.fail(ReturnCode.OUT_OF_RANGE_ERROR);
        }
        hotCache.put(longDomain, shortDomain);
        idToLongDomainCache.put(newId, longDomain);
        return Result.success(shortDomain);
    }

    @Override
    public Result<String> getLongDomain(String shortDomain) {
        Long key = ConversionUtil.decode62To10(shortDomain);
        String longDomain = idToLongDomainCache.getIfPresent(key);
        if (StringUtils.isNotBlank(longDomain)) {
            return Result.success(longDomain);
        } else {
            return Result.fail(ReturnCode.NOT_EXISTS_ERROR);
        }
    }
}
