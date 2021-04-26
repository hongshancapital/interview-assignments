package com.wxp.service.impl;

import com.wxp.service.ShortUrlService;
import com.wxp.util.Base62;
import com.wxp.util.SnowFlake;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("shortUrlLocalService")
public class ShortUrlLocalServiceImpl implements ShortUrlService {

    /**
     * 短链接存储Map(短链接对原始链接)
     * short url -> long url
     */
    private static Map<String, String> shortUrlMap = new ConcurrentHashMap<String, String>();
    /**
     * 短链接存储Map(原始链接对短链接)
     * long url -> short url
     */
    private static Map<String, String> reverseShortUrlMap = new ConcurrentHashMap<String, String>();

    /**
     * 获取短连接
     *
     * @param longUrl 原始链接
     * @return 短连接
     */
    public String getShortUrl(String longUrl) {
        String shortUrl = reverseShortUrlMap.get(longUrl);
        // 是否已存在短链接
        if(!StringUtils.isEmpty(shortUrl)) {
            // 二次确认，存在且一致则返回，不存在则继续后续处理
            String tmpLongUrl = shortUrlMap.get(shortUrl);
            if(longUrl.equals(tmpLongUrl)) {
                return shortUrl;
            }
        }

        // 用类雪花算法生成唯一ID
        long id = SnowFlake.nextId();
        // 转换为62进制
        shortUrl = Base62.encode(id);

        // 存储短链接
        shortUrlMap.put(shortUrl, longUrl);
        reverseShortUrlMap.put(longUrl, shortUrl);
        return shortUrl;
    }

    /**
     * 根据短链接获取原始链接
     *
     * @param shortUrl 短连接
     * @return 原始链接
     */
    public String getOriginUrl(String shortUrl) {
        return shortUrlMap.get(shortUrl);
    }
}
