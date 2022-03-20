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

    private final static String KEY_SHORT_LINK = "KEY_SHORT_LINK";

    private final static String KEY_ORIGINAL_LINK = "KEY_ORIGINAL_LINK";

    @Autowired
    private ShortLinkCache shortLinkCache;

    public void save(String shortLink, String originalLink) {

        shortLinkCache.putValue(KEY_SHORT_LINK, shortLink, originalLink);

        shortLinkCache.putValue(KEY_ORIGINAL_LINK, originalLink, shortLink);

    }

    public String findByShortLink(String shortLink){

       return shortLinkCache.getValue(KEY_SHORT_LINK, shortLink);

    }

    public String findByOriginalLink(String originalLink){
        return shortLinkCache.getValue(KEY_ORIGINAL_LINK, originalLink);
    }

}
