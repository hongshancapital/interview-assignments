package com.skylu.service.impl;

import com.skylu.constants.LocalCache;
import com.skylu.service.UrlMappingService;
import com.skylu.utils.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName UrlMappingServiceImpl.java
 * @Description url映射接口实现类
 * @createTime 2022年04月22日 15:38:00
 */
@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    @Override
    public String longToShort(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return null;
        }
        String [] shorts = UrlUtil.shortUrl(longUrl);
        if (shorts == null || shorts.length == 0) {
            return null;
        }
        return getShort(shorts, longUrl);
    }

    @Override
    public String shortToLong(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return null;
        }
         String longUrl =  LocalCache.CACHE.get(shortUrl);
         if (StringUtils.isNotBlank(longUrl)) {
             //移动缓存位置
             //先删
             LocalCache.CACHE.remove(shortUrl);
             LocalCache.RECORD.remove(longUrl);
             //后插
             LocalCache.CACHE.put(shortUrl, longUrl);
             LocalCache.RECORD.put(longUrl, shortUrl);
         }
        return longUrl;
    }

    private String getShort(String[] shorts, String longUrl) {
        String shortCode = null;
        // 如果已缓存，则直接返回
        if (LocalCache.RECORD.containsKey(longUrl)) {
            return LocalCache.RECORD.get(longUrl);
        }
        // 从第一组编码开始匹配
        for (String st : shorts) {
           if (!LocalCache.CACHE.containsKey(st)) {
               store(st,longUrl);
               shortCode = st;
               break;
           }
        }
        return shortCode;
    }

    /**
     * 保存长短url关系
     * @param st 短域名
     * @param longUrl 长域名
     */
    private void store(String st, String longUrl) {

        // 如果当前缓存超过最大容量，先删除部分旧值
        if (LocalCache.CACHE.size() >= LocalCache.MAX) {
            int count = 0;
            Iterator<String> iterator =  LocalCache.CACHE.keySet().iterator();
            while (count < LocalCache.BATCH_NUM  && iterator.hasNext()) {
                String key = iterator.next();
                // 长域名
                String value = LocalCache.CACHE.get(key);
                //删除缓存数据
                LocalCache.RECORD.remove(value);
                iterator.remove();
                count++;
            }
        }
        LocalCache.CACHE.put(st, longUrl);
        LocalCache.RECORD.put(longUrl,st);
    }
}
