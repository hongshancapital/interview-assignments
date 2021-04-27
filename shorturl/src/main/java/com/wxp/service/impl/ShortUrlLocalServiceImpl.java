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

    // 短链接生成中占位符
    private static String PLACEHOLDER = "#";

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
        try {
            /**
             * 置入"处理中占位符"。
             *      返回空时，当前链接未处理，正常处理；
             *      返回PLACEHOLDER时，当前链接处理中，等待处理；
             *      返回其他时，表示短链接已生成，返回。
             */
            String shortUrl = reverseShortUrlMap.putIfAbsent(longUrl, PLACEHOLDER);
            // 返回"处理中占位符"，等待处理完成
            if(PLACEHOLDER.equals(shortUrl)) {
                while (true) {
                    shortUrl = reverseShortUrlMap.putIfAbsent(longUrl, PLACEHOLDER);
                    if(!PLACEHOLDER.equals(shortUrl)) {
                        break;
                    }
                }
            }
            // 短链接已经生成
            if(!StringUtils.isEmpty(shortUrl) && !PLACEHOLDER.equals(shortUrl)) {
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
        } catch (Exception e) {
            // 发生异常时，清理占位符
            reverseShortUrlMap.remove(longUrl);
            throw e;
        }
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
