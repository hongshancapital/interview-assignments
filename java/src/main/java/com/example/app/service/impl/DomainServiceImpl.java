package com.example.app.service.impl;

import com.example.app.common.constants.Constants;
import com.example.app.common.exception.ModuleException;
import com.example.app.common.utils.GenerateRandomKey;
import com.example.app.common.utils.LRUCache;
import com.example.app.service.DomainService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author voidm
 * @date 2021/9/18
 */
@Service
public class DomainServiceImpl implements DomainService {

    private final LRUCache <String,String> cacheByShortUrl = new LRUCache<>(Constants.LRU_CACHE_MAX_LEN);
    private final LRUCache <String,String> cacheByFullUrl = new LRUCache<>(Constants.LRU_CACHE_MAX_LEN);

    public int getCacheByShortUrlSize() {
        return cacheByShortUrl.size();
    }

    public int getCacheByFullUrlSize() {
        return cacheByFullUrl.size();
    }

    @Override
    public String generateShortUrl(String fullUrl) throws ModuleException {

        String shortUrl = cacheByFullUrl.get(fullUrl);
        if (!StringUtils.isEmpty(shortUrl)) {
            return shortUrl;
        }

        // 随机生成短链接 key
        String key = GenerateRandomKey.generateRandomKey(Constants.KEY_MAX_LEN);
        shortUrl = Constants.DOMAIN + key;
        // 同时缓存长短链接
        cacheByShortUrl.put(shortUrl,fullUrl);
        cacheByFullUrl.put(fullUrl,shortUrl);

        return shortUrl;
    }

    @Override
    public String parseWithShortUrl(String shortUrl) throws ModuleException {
        String fullUrl = cacheByShortUrl.get(shortUrl);
        if (StringUtils.isEmpty(fullUrl)) {
            throw new ModuleException(String.format("该短链接{%s}未匹配到全链接,可能过期已删除", shortUrl));
        }
        return fullUrl;
    }
}