package com.mjl.repository;

import com.mjl.api.ShortUrlRepository;
import com.mjl.exception.BusinessException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    /**
     * 最大容量
     */
    private static long MAX_SIZE = 1000000L;

    /**
     * 默认短链接有效期 10min
     */
    private static long EXPIRE_TIME = 10 * 60 * 1000;

    private Map<String, String> short2LongUrlMap;
    private Map<String, String> long2ShortUrlMap;
    private Map<String, Long> shortUrlExpireTimeMap;
    private AtomicLong currSize;

    public static void setExpireTime(long expireTime) {
        ShortUrlRepositoryImpl.EXPIRE_TIME = expireTime;
    }

    public static void setMaxSize(long maxSize) {
        MAX_SIZE = maxSize;
    }

    public void clear() {
        short2LongUrlMap.clear();
        long2ShortUrlMap.clear();
        shortUrlExpireTimeMap.clear();
    }

    @PostConstruct
    private void initMap() {
        long2ShortUrlMap = new ConcurrentHashMap<>();
        short2LongUrlMap = new ConcurrentHashMap<>();
        shortUrlExpireTimeMap = new ConcurrentHashMap<>();
        currSize = new AtomicLong(0);
    }

    @Override
    public String getLongUrlFromShortUrlSuffix(String shortUrl) {
        String longUrl = short2LongUrlMap.get(shortUrl);
        if (longUrl != null) {
            Long expireTimestamp = shortUrlExpireTimeMap.get(shortUrl);
            if (expireTimestamp == null || expireTimestamp < System.currentTimeMillis()) {
                short2LongUrlMap.remove(shortUrl);
                long2ShortUrlMap.remove(longUrl);
                shortUrlExpireTimeMap.remove(shortUrl);
                currSize.decrementAndGet();
                return null;
            }
            return longUrl;
        }
        return null;
    }

    @Override
    public String getShortUrlSuffixFromLongUrl(String longUrl) {
        String shortUrl = long2ShortUrlMap.get(longUrl);
        if (shortUrl != null) {
            Long expireTimestamp = shortUrlExpireTimeMap.get(shortUrl);
            if (expireTimestamp == null || expireTimestamp < System.currentTimeMillis()) {
                short2LongUrlMap.remove(shortUrl);
                long2ShortUrlMap.remove(longUrl);
                shortUrlExpireTimeMap.remove(shortUrl);
                currSize.decrementAndGet();
                return null;
            }
            return shortUrl;
        }
        return null;
    }

    @Override
    public void rmOverdueUrl() {
        // 遍历删除过期的数据
        for (Map.Entry<String, Long> entry : shortUrlExpireTimeMap.entrySet()) {
            if (entry.getValue() < System.currentTimeMillis()) {
                String shortUrl = entry.getKey();
                String longUrl = short2LongUrlMap.get(shortUrl);
                short2LongUrlMap.remove(entry.getKey());
                currSize.decrementAndGet();
                if (longUrl != null) {
                    long2ShortUrlMap.remove(longUrl);
                }
                shortUrlExpireTimeMap.remove(shortUrl);
            }
        }

    }

    @Override
    public void saveUrlMap(String longUrl, String shortUrl) {
        long size = currSize.addAndGet(1);
        if (size > MAX_SIZE) {
            rmOverdueUrl();
        }
        size = currSize.get();
        if (size > MAX_SIZE) {
            currSize.decrementAndGet();
            throw new BusinessException("SAVE-URL-EXCEED-MAX-SIZE-ERROR");
        }

        short2LongUrlMap.put(shortUrl, longUrl);
        long2ShortUrlMap.put(longUrl, shortUrl);
        shortUrlExpireTimeMap.put(shortUrl, System.currentTimeMillis() + EXPIRE_TIME);
    }

}
