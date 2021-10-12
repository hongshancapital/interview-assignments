package com.assignment.service;

import com.assignment.utils.ConvertUtil;
import com.assignment.utils.LRUCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author mrdiyewu@gmail.com
 * @date 2021/10/11 18:47
 */
@Service
public class ConvertService {
    /**
     * 测试环境缓存，生成应使用数据库存储
     */
    private static LRUCache<String, String> cacheKeyLong = new LRUCache<>(2000);
    private static LRUCache<String, String> cacheKeyShort = new LRUCache<>(2000);

    public String l2s(String longUrl){
        String cacheValue = cacheKeyLong.get(longUrl);
        String keyShortUrl = StringUtils.isNotBlank(cacheValue) ? cacheValue : ConvertUtil.ShortUrl(longUrl);
        cacheKeyShort.put(keyShortUrl, longUrl);
        cacheKeyLong.put(longUrl, keyShortUrl);
        return keyShortUrl;
    }
    public String s2l(String shortUrl){
        String cacheLongUrl = cacheKeyShort.get(shortUrl);
        if(StringUtils.isBlank(cacheLongUrl)){
            throw new RuntimeException("您输入的参数有误，请重试");
        }
        return cacheLongUrl;
    }
}
