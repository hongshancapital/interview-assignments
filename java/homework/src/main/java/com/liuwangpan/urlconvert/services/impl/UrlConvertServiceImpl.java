package com.liuwangpan.urlconvert.services.impl;

import com.google.common.hash.Hashing;
import com.liuwangpan.urlconvert.common.UrlConvertException;
import com.liuwangpan.urlconvert.configs.ApplicationConfig;
import com.liuwangpan.urlconvert.enums.ExceptionEnum;
import com.liuwangpan.urlconvert.services.UrlConvertService;
import com.liuwangpan.urlconvert.utils.Base62Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Deacription 短地址核心处理
 * @author wp_li
 **/
@Service
@Slf4j
public class UrlConvertServiceImpl implements UrlConvertService {

    @Autowired
    private CacheManager cacheManager;

    /**
     * 缓存key
     */
    private static final String SHORT_URL_CACHE_KEY = "shortUrlMapping";

    /**
     * hash碰撞时,加固定前缀
     */
    private static final String PREFIX_SALT = "_";
    /**
     * 统计hash冲突
     */
    private static AtomicLong hashCollisionCounter = new AtomicLong();


    /**
     * 1.生成短地址
     * 2.处理hash冲突
     * 3.存储到缓存
     *
     * @param longUrl
     * @return
     */
    @Override
    public String generateShortUrl(String longUrl) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        longUrl = longUrl.toLowerCase();
        String encode62ShortUrl = generate62ShortUrl(longUrl);
        encode62ShortUrl = processHashCollision(encode62ShortUrl, longUrl);
        cacheManager.getCache(SHORT_URL_CACHE_KEY).put(encode62ShortUrl, formatLongUrl(longUrl));
        stopWatch.stop();
        return ApplicationConfig.SHORT_DOMAIN_PATH + encode62ShortUrl;
    }


    /**
     * 根据短地址获取长地址
     *
     * @param shortUrl
     * @return
     */
    @Override
    public String getLongUrl(String shortUrl) {
        shortUrl = shortUrl.replace(ApplicationConfig.SHORT_DOMAIN_PATH, "");
        Cache cache = cacheManager.getCache(SHORT_URL_CACHE_KEY);
        if (cache == null || cache.get(shortUrl) == null) {
            throw new UrlConvertException(ExceptionEnum.NOT_FOUND);
        }
        String longUrl = cache.get(shortUrl).get().toString();
        return formatLongUrl(longUrl);
    }

    /**
     * 统计hash碰撞
     *
     * @return
     */
    @Override
    public Long getHashCollisionCount() {
        return hashCollisionCounter.get();
    }


    /**
     * 生成62位的短地址
     * <p>
     * 1.先通过Hashing.murmur3_32 计算
     * 2.再把Long转成62进制变得更短
     * </p>
     *
     * @param longUrl
     * @return
     */
    private String generate62ShortUrl(String longUrl) {
        Long shortUrlId = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).padToLong();
        String encode62ShortUrl = Base62Encoder.encode(shortUrlId);
        return encode62ShortUrl;
    }

    /**
     * 处理hash冲突,递归处理
     *
     * @param encode62ShortUrl
     * @param longUrl
     * @return
     */
    private String processHashCollision(String encode62ShortUrl, String longUrl) {
        Cache cache = cacheManager.getCache(SHORT_URL_CACHE_KEY);
        if (cache != null && cache.get(encode62ShortUrl) != null) {
            String cacheLongUrl = cache.get(encode62ShortUrl).get().toString();
            if (!longUrl.equals(cacheLongUrl.toLowerCase())) {
                hashCollisionCounter.incrementAndGet();
                log.info("hash冲突 encode62ShortUrl={}, longUrl={}", encode62ShortUrl, longUrl);
                longUrl = formatLongUrl(longUrl);
                longUrl = (char) (Math.random() * 26 + 'a') + PREFIX_SALT + longUrl;
                encode62ShortUrl = generate62ShortUrl(longUrl);
                return this.processHashCollision(encode62ShortUrl, longUrl);
            }
        }
        return encode62ShortUrl;
    }

    /**
     * 如果长地址包含特殊前缀，则去除前缀
     *
     * @param longUrl
     * @return
     */
    public String formatLongUrl(String longUrl) {
        if (longUrl.indexOf(PREFIX_SALT) == 1) {
            longUrl = longUrl.substring(2, longUrl.length());
        }
        return longUrl;
    }

}