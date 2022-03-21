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

    /**
     * 保存短链映射关系
     * @param shortLink 短域名
     * @param originalLink 原域名
     */
    public void save(String shortLink, String originalLink) {

        shortLinkCache.putValue(KEY_SHORT_LINK, shortLink, originalLink);
        // 同时存储，用于幂等查询
        shortLinkCache.putValue(KEY_ORIGINAL_LINK, originalLink, shortLink);

    }

    /**
     * 根据短链查询原域名
     * @param shortLink 短域名
     * @return 原域名
     */
    public String findByShortLink(String shortLink){

       return shortLinkCache.getValue(KEY_SHORT_LINK, shortLink);
    }

    /**
     * 根据原域名查询短域名
     * @param originalLink 原域名
     * @return 短域名
     */
    public String findByOriginalLink(String originalLink){
        return shortLinkCache.getValue(KEY_ORIGINAL_LINK, originalLink);
    }

}
