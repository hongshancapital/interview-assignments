package com.xwc.example.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xwc.example.commons.utils.CompressUtils;
import com.xwc.example.service.DomainService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类描述：通过第三方缓存实现的业务短域名服务的功能
 * 这种只适合单机服务
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 18:17
 */
@Component
public class CaffeineDomainServiceImpl implements DomainService, InitializingBean {
    private Cache<String, String> longDomainCache;
    private Cache<String, String> shortDomainCache;

    @Value("${domain.shortHost:http://127.0.0.1:8080/}")
    private String shortHost;

    @Value("${domain.maxCapacity:10000000}")
    private Long maxCapacity;

    // 计数器
    private AtomicLong count = new AtomicLong();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.longDomainCache = Caffeine.newBuilder()
                .expireAfterWrite(14, TimeUnit.SECONDS)
                .expireAfterAccess(14, TimeUnit.SECONDS)
                .maximumSize(maxCapacity)
                .build();
        this.shortDomainCache = Caffeine.newBuilder()
                .expireAfterWrite(14, TimeUnit.SECONDS)
                .expireAfterAccess(14, TimeUnit.SECONDS)
                .maximumSize(maxCapacity)
                .build();
    }

    /**
     * 通过一个长域名返回一个短域名
     *
     * @param longDomain 长域名地址
     * @return 返回一个短域名信息
     */
    @Override
    public String getShortDomain(String longDomain) {
        String shortPathVariable = longDomainCache.get(longDomain, val -> {
            String newShortPathVariable = CompressUtils.compressNumber(count.incrementAndGet());
            shortDomainCache.put(newShortPathVariable, longDomain);
            return newShortPathVariable;
        });
        return shortHost + shortPathVariable;
    }

    /**
     * 通过短域名查找一个长域名信息
     *
     * @param shortAddress 短域名地址
     * @return 长域名信息
     */
    @Override
    public String findLengthDomain(String shortAddress) {
        String shortPathVariable = shortAddress.substring(Math.min(shortAddress.length(), shortHost.length()));
        if (!StringUtils.hasText(shortPathVariable)) {
            return null;
        }
        return shortDomainCache.get(shortPathVariable, val -> null);
    }

    @Override
    public void clear() {
        shortDomainCache.cleanUp();
        longDomainCache.cleanUp();
        count.set(0);
    }


}
