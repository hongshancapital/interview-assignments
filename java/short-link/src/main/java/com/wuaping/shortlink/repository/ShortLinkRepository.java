package com.wuaping.shortlink.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短域名仓库层
 *
 * @author Aping
 * @since 2022/3/19 13:08
 */
@Service
public class ShortLinkRepository {

    /**
     * 以短链为key的缓存结构
     */
    private final static String KEY_SHORT_LINK = "KEY_SHORT_LINK";

    /**
     * 以原链为key的缓存结构，用于幂等请求查询
     */
    private final static String KEY_ORIGINAL_LINK = "KEY_ORIGINAL_LINK";

    @Autowired
    private ShortLinkCache shortLinkCache;

    public void save(String shortLink, String originalLink) {

        shortLinkCache.putValue(KEY_SHORT_LINK, shortLink, originalLink);
        // 同时存储，用于幂等查询
        shortLinkCache.putValue(KEY_ORIGINAL_LINK, originalLink, shortLink);

    }

    public String findByShortLink(String shortLink){

       return shortLinkCache.getValue(KEY_SHORT_LINK, shortLink);

    }

    public String findByOriginalLink(String originalLink){
        return shortLinkCache.getValue(KEY_ORIGINAL_LINK, originalLink);
    }

}
