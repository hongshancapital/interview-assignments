package com.scdt.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名服务层
 *
 * @author penghai
 * @date 2022/5/3
 */
@Service
public class ShortUrlService {

    private static final int RADIX = 62;

    private static final AtomicLong sequence = new AtomicLong(10000000);

    private static final long NUM62POW8 = 218340105584896L;

    private final Cache<String, String> cache;

    private static final String DIGITS = "Ij4ck6M8RAh70zquLnKmJgDe5FfoNWxUYwrXbiQa3Hvt2VyEGlBsP91pSZTdCO";

    public ShortUrlService(Cache<String, String> cache) {
        this.cache = cache;
    }

    public Optional<String> getShortUrl(String longUrl) {
        // 基于自增id算法生成一个短域名
        // 注意此处无需判断cache缓存中是否已经存在longUrl，详细见设计文档 2.3 业务流程设计
        String shorUrl = generateShortUrl();
        if (shorUrl.isEmpty()) {
            return Optional.empty();
        }

        // 缓存短域名到长域名的映射
        cache.put(shorUrl, longUrl);

        // 返回短域名
        return Optional.of(shorUrl);
    }

    public Optional<String> getLongUrl(String shortUrl) {
        return Optional.ofNullable(cache.getIfPresent(shortUrl));
    }

    private static String generateShortUrl() {
        long id = sequence.incrementAndGet();
        // 短域名限定最大长度为8位，以此这里限制id上限为62^8
        if (id > NUM62POW8) {
            return "";
        }
        return to62RadixString(id);
    }

    private static String to62RadixString(long id) {
        StringBuilder sBuilder = new StringBuilder();
        do {
            int remainder = (int) (id % RADIX);
            sBuilder.append(DIGITS.charAt(remainder));
            id = id / RADIX;
        } while (id != 0);
        return sBuilder.toString();
    }
}
