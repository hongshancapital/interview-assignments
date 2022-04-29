package com.zp.service.impl;

import com.zp.cache.LRUCache;
import com.zp.service.ShortUrlService;
import com.zp.utils.RadixUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.util.StringUtils;

/**
 * 短域名Service实现类
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 短域名字符数组
     */
    private char[] CHAR_ARRAY = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'
    };

    /**
     * 自增id
     */
    private AtomicLong index = new AtomicLong(1199812800L);

    /**
     * LRU容量
     */
    @Value("${lru.max.capacity}")
    private int capacity;

    /**
     * 生成短域名的前缀
     */
    @Value("${base.url}")
    private String baseUrl;

    /**
     * 短域名与长域名映射关系缓存
     */
    LRUCache<String, String> short2LongCache;

    /**
     * 长域名与短域名映射关系缓存
     * 此缓存的目的是避免相同的长域名重复生成短域名
     */
    LRUCache<String, String> long2ShortCache;

    @PostConstruct
    private void init() {
        short2LongCache = new LRUCache<>(capacity);
        long2ShortCache = new LRUCache<>(capacity);
    }

    /**
     * 根据长域名获取短域名
     *
     * @param url 长域名
     * @return 短域名
     */
    @Override
    public String getShortUrl(String url) {
        String shortUrl = long2ShortCache.get(url);
        if (!StringUtils.isEmpty(shortUrl)) {
            // 如果缓存中有，直接返回
            return shortUrl;
        }
        // 获取自增id
        long id = index.incrementAndGet();
        // 转为62进制
        shortUrl = baseUrl + RadixUtil.transRadix(CHAR_ARRAY, id, 62);
        // 放入映射关系缓存
        short2LongCache.put(shortUrl, url);
        long2ShortCache.put(url, shortUrl);

        return shortUrl;
    }

    /**
     * 根据短域名获取长域名
     *
     * @param url 短域名
     * @return 长域名
     */
    @Override
    public String getLongUrl(String url) {
        // 从缓存中获取
        return short2LongCache.get(url);
    }
}
