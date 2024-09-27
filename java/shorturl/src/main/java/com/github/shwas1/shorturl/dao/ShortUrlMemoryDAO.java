package com.github.shwas1.shorturl.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.shwas1.shorturl.model.ShortUrlPO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 使用JVM内存存储，并设置最大数量，防止OOM
 */
@Repository
public class ShortUrlMemoryDAO implements ShortUrlDAO {
    /**
     * 最大数量限制，防止内存溢出
     */
    private static final int MAXIMUM_SIZE = 1024_000;
    @Value("${short.url.maxsize}")
    private int maxSize;
    private Cache<String, ShortUrlPO> shortUrlRepo;
    private Cache<String, ShortUrlPO> originalUrlRepo;
    @Value("${short.url.idempotent}")
    private Boolean idempotent;

    @PostConstruct
    public void init() {
        shortUrlRepo = Caffeine.newBuilder()
                .initialCapacity(0)
                .maximumSize(maxSize)
                .build();

        originalUrlRepo = Caffeine.newBuilder()
                .initialCapacity(0)
                .maximumSize(maxSize)
                .build();
    }

    @Override
    public ShortUrlPO save(ShortUrlPO shortUrlPO) {
        Assert.hasLength(shortUrlPO.getShortUrl(), "短链接不能为空");
        Assert.hasLength(shortUrlPO.getOriginalUrl(), "原始链接不能为空");

        shortUrlPO.setCreateTime(new Date());

        if (idempotent) {
            return originalUrlRepo.get(shortUrlPO.getOriginalUrl(), originalUrl -> {
                shortUrlRepo.put(shortUrlPO.getShortUrl(), shortUrlPO);
                return shortUrlPO;
            });
        }
        return put(shortUrlPO);

    }

    private ShortUrlPO put(ShortUrlPO shortUrlPO) {
        originalUrlRepo.put(shortUrlPO.getOriginalUrl(), shortUrlPO);
        shortUrlRepo.put(shortUrlPO.getShortUrl(), shortUrlPO);
        return shortUrlPO;
    }

    @Override
    public ShortUrlPO getByShortUrl(String shortUrl) {
        return shortUrlRepo.getIfPresent(shortUrl);
    }


    void setIdempotent(Boolean idempotent) {
        this.idempotent = idempotent;
    }
}
