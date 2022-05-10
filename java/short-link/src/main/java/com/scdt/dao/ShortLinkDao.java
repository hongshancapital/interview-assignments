package com.scdt.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.scdt.domin.po.ShortLinkInfo;
import org.springframework.stereotype.Repository;

/**
 * ShortLinkDao
 *
 * @author weixiao
 * @date 2022-04-26 16:39
 */
@Repository
public class ShortLinkDao {
    /** 使用缓存存储，最大记录数使用总内存的1/3除以1000（每个记录按照1000字节估算） */
    private static final Cache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(Runtime.getRuntime().totalMemory() / (3 * 1000))
            .build();

    public int save(ShortLinkInfo shortLinkInfo) {
        cache.put(shortLinkInfo.getShortLink(), shortLinkInfo.getLongLink());
        return 1;
    }

    public String getLongLink(String shortLink) {
        return cache.getIfPresent(shortLink);
    }
}
