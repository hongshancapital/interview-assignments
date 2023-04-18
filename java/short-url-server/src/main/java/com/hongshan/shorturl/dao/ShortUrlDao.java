package com.hongshan.shorturl.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.hongshan.shorturl.domain.entity.ShortUrlEntity;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 * @version: 1.0
 */
@Component
public class ShortUrlDao {
    /**
     * 存储原始链接hash 与原始链接的映射，防止相同长链接的重复提交，缓存时间30分钟
     */
    private Cache<String, ShortUrlEntity> DUPLICATE_CACHE = Caffeine.newBuilder()
            // 过期时间写入后30分钟
            .expireAfterWrite(30, TimeUnit.MINUTES)
            // 最大存储100万（键值对）
            .maximumSize(1_000_000)
            .build();
    /**
     * 存储短链接 与 原始链接的映射关系，最大存储1亿条记录
     */
    private Cache<String, ShortUrlEntity> SHORT_URL_ENTITY_CACHE = Caffeine.newBuilder()
            // 根据key的过期时间设置
            .expireAfter(new ExpireStrategy())
            // 最大存储1亿个短链接
            .maximumSize(1_000_000_000)
            .removalListener((k, v, cause) -> DUPLICATE_CACHE.invalidate(k))
            .build();

    /**
     * 存储短链接key和原始链接的映射关系，并存储一份hash和原始链接的映射
     *
     * @param shortUrlEntity
     * @return
     * @throws
     * @date 2022/3/20
     * @since 1.0
     */
    public void add(ShortUrlEntity shortUrlEntity) {
        SHORT_URL_ENTITY_CACHE.put(shortUrlEntity.getShortKey(), shortUrlEntity);
        DUPLICATE_CACHE.put(shortUrlEntity.getHash(), shortUrlEntity);
    }

    /**
     * 根据短域名后缀获取原始域名信息
     *
     * @param key short url suffix
     * @return {@link ShortUrlEntity}
     * @throws
     * @date 2022/3/19
     * @since 1.0
     */
    public ShortUrlEntity findByKey(String key) {
        ShortUrlEntity shortUrlEntity = SHORT_URL_ENTITY_CACHE.get(key, str -> null);
        return shortUrlEntity;
    }

    /**
     * 根据hash和原始链接查找是否已经存储
     *
     * @param hash
     * @return {@link ShortUrlEntity}
     * @throws
     * @date 2022/3/20
     * @since 1.0
     */
    public ShortUrlEntity findByHash(String hash, String originUrl) {
        ShortUrlEntity shortUrlEntity = DUPLICATE_CACHE.getIfPresent(hash);
        if (Objects.nonNull(shortUrlEntity) && shortUrlEntity.getOriginUrl().equals(originUrl)) {
            return shortUrlEntity;
        }
        return null;
    }


    private static class ExpireStrategy implements Expiry<String, ShortUrlEntity> {
        @Override
        public long expireAfterCreate(@NonNull String s, @NonNull ShortUrlEntity shortUrlEntity, long currentTime) {
            if (Objects.nonNull(shortUrlEntity.getExpireAt())) {
                long expireTimeMillis = shortUrlEntity.getExpireAt();
                return TimeUnit.MILLISECONDS.toNanos(expireTimeMillis) - currentTime;
            }
            return Long.MAX_VALUE;
        }

        @Override
        public long expireAfterUpdate(@NonNull String s, @NonNull ShortUrlEntity shortUrlEntity, long currentTime, @NonNegative long currentDuration) {
            return currentDuration;
        }

        @Override
        public long expireAfterRead(@NonNull String s, @NonNull ShortUrlEntity shortUrlEntity, long currentTime, @NonNegative long currentDuration) {
            return currentDuration;
        }
    }
}
