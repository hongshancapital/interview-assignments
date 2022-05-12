package com.example.urltransform.service;

import com.example.urltransform.util.UrlTransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author  fengguangwu
 * createTime  2022/5/9
 * desc
 **/
@Slf4j
@Service
public class UrlTransformServiceImpl implements UrlTransformService{
    
    public static Map<String,String> urlCacheMap = new ConcurrentHashMap<>();
    
    @Value("${url.md5.length:6}")
    private int urlLength;

    /**
     * 通过MD5方式获取original Url
     * @param shortUrl
     * @return original Url
     */
    @Override
    public String getOriginalUrl(String shortUrl) {
        if (urlCacheMap.containsKey(shortUrl)){
            return urlCacheMap.get(shortUrl);
        }
        throw new RuntimeException("未找到对应的 orignal url");
    }

    /**
     * 保存original
     * @param longUrl
     * @return short url
     */
    @Override
    public String saveOriginalUrl(String longUrl) {
        String shortUrl = UrlTransformUtil.getShortUrlWithMd5(longUrl,urlLength);
        if (StringUtils.isBlank(shortUrl)){
            throw new RuntimeException("short url resp is blank");
        }
        if (UrlTransformUtil.getMapCapacity(urlCacheMap) > UrlTransformUtil.MAXIMUM_CAPACITY){
            throw new RuntimeException("cache map is over capacity");
        }
        if (!urlCacheMap.containsKey(shortUrl)){
            urlCacheMap.put(shortUrl,longUrl);
        }
        return shortUrl;
    }
}
