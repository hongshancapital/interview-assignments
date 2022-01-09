package com.example.demo.service;

import com.example.demo.bean.po.ShortUrlPo;
import com.example.demo.config.AppConfig;
import com.example.demo.utils.Base62;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@Service
public class ShortLinkCache {

    /**
     * 下一个短连接ID
     */
    private final AtomicLong aLong = new AtomicLong();

    @Resource
    AppConfig appConfig;

    /**
     * 短连接缓存
     */
    private Cache<String, ShortUrlPo> shortUrlCache;

    @PostConstruct
    public void init() {
        aLong.set(appConfig.getStartSerialNumber());
        shortUrlCache = CacheBuilder.newBuilder()
                .initialCapacity(8)
                .maximumSize(appConfig.getCacheSize())
                .expireAfterAccess(appConfig.getCacheExpire(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * 获取下一个短连接对象
     */
    public ShortUrlPo next() {
        long id = aLong.getAndIncrement();
        ShortUrlPo shortUrlPo = new ShortUrlPo();
        shortUrlPo.setId(id);
        shortUrlPo.setCode(Base62.encode(id));
        return shortUrlPo;
    }

    public void add(String code, ShortUrlPo shortUrlPo) {
        shortUrlCache.put(code, shortUrlPo);
    }

    public ShortUrlPo getShortUrl(String code) {
        if (code == null || code.length() == 0) {
            return null;
        }
        return shortUrlCache.getIfPresent(code);
    }

}
