package com.qyboot.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zk
 */
public class Base62UrlShorter {


    private AtomicLong incr = new AtomicLong(0);

    Map<String,String> longUrlIdMap = new ConcurrentHashMap();

    private int maxTinyUriCached = 6000000;

    private Cache<String, String> cahced;

    public Base62UrlShorter () {
        cahced = CacheBuilder.newBuilder()
                .maximumSize(maxTinyUriCached)
                .removalListener((RemovalListener<String, String>) notification -> {
                    String tinyUrl = notification.getValue();
                    if (null != tinyUrl) {
                        longUrlIdMap.remove(tinyUrl);
                    }
                })
                .expireAfterWrite(Long.MAX_VALUE, TimeUnit.DAYS).build();
    }

    public long  incr(){
        return incr.incrementAndGet();
    }

    public String shorten(String longUrl){
        String shortUrl = null;
        try {
            shortUrl = cahced.get(longUrl, () -> {
                long id = incr();
                String baseShortUrl = Base62.toBase62(id);
                longUrlIdMap.put(baseShortUrl, longUrl);
                return baseShortUrl;
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return shortUrl;

    }

    public String lookup(String shortUrl){
        return longUrlIdMap.get(shortUrl);
    }

    static class Base62 {
        private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static String toBase62(long num) {
            StringBuilder sb = new StringBuilder();
            do {
                int i = (int) (num % 62);
                sb.append(BASE.charAt(i));
                num /= 62;
            } while (num > 0);

            return sb.reverse().toString();
        }
    }
}
