package com.jk.shorturl.dao.storage;

import com.jk.shorturl.Utils.LRUCache;
import com.jk.shorturl.config.ConfigMainUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 域名内存存储器
 */
@Slf4j
@Repository
public class ShortCodeStorage {
    public int CACHE_MAX_SIZE = 10000;
    private LRUCache<String, String> SHORT_LONG = new LRUCache(CACHE_MAX_SIZE);
    private LRUCache<String, String> LONG_SHORT = new LRUCache(CACHE_MAX_SIZE);

     /**
     * 根据短域名换取长域名
     *
     * @param shortCode
     * @return 存在返回长域名 / 如果不存在或参数为空，返回 null,
     */
    public String getLongURLByshortCode(String shortCode) {

        if (!StringUtils.hasLength(shortCode)) {
            return null;
        }
        return SHORT_LONG.get(shortCode);
    }

    /**
     * 根据长域名换取短域名
     *
     * @param longURL
     * @return 存在返回短域名 / 如果不存在或参数为空，返回 null,
     */
    public String getShortCodeByLongURL(String longURL) {
        if (!StringUtils.hasLength(longURL)) {
            return null;
        }
        return LONG_SHORT.get(longURL);
    }

    /**
     * 保存短域名和长域名对应关系
     *
     * @param shortCode
     * @param longURL
     * @return
     */
    public Boolean saveShortCode(String shortCode, String longURL) {
        if (ObjectUtils.isEmpty(shortCode) || ObjectUtils.isEmpty(longURL)) {
            return null;
        }
        SHORT_LONG.put(shortCode, longURL);
        LONG_SHORT.put(longURL, shortCode);
        return true;
    }
}
