package com.example.demo.service;

import com.example.demo.util.ShortUrlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author chenlong
 * @date 2021-04-20
 */
@Service
public class UrlGeneratorService {

    /**
     * 预计需存储的最大URL数量
     */
    private final static int MAX_URL_COUNT = 1000000;

    /**
     * 存放短链接和原始链接映射
     */
    private static HashMap<String, String> urlMap = null;

    // 初始化map
    static {
        // 根据预计需存储的最大URL数量计算HashMap的初始容量，考虑内存限制
        int initialCapacity = Math.round(MAX_URL_COUNT / 0.75F + 1.0F);
        urlMap = new HashMap<>(initialCapacity);
    }

    /**
     * 原始链接生成短链接
     * @param url   原始链接
     * @return      短链接
     */
    public String generateShortUrl(String url){
        String[] shortUrls = ShortUrlGenerator.generateShortUrl(url);

        boolean isSuccess = false;
        String shortUrl = null;
        for(int i = 0; i < shortUrls.length; i++) {
            shortUrl = shortUrls[i];
            // 短链接已经存在
            if (urlMap.containsKey(shortUrl)) {
                String originUrl = urlMap.get(shortUrl);
                if (StringUtils.isNotBlank(originUrl)) {
                    if (originUrl.equals(url)) {
                        // 短链接已存在，并且原始链接相同
                        isSuccess = true;
                        break;
                    }else {
                        // 短链接已存在，并且原始链接不同，那么判断下一个生成的短链接
                        continue;
                    }
                }else {
                    // 短链接已存在，并且原始链接存的是空
                    urlMap.put(shortUrl, url);
                    isSuccess = true;
                    break;
                }
            } else {
                // 不存在，则将生成好的短链接放到urlMap中
                urlMap.put(shortUrl, url);
                isSuccess = true;
                break;
            }
        }

        if(isSuccess) {
            return shortUrl;
        }else {
            // 如果发生碰撞，使用8位随机字符串
            shortUrl = ShortUrlGenerator.generateRandomShortUrl(url, 8);
            urlMap.put(shortUrl, url);
            return shortUrl;
        }
    }

    public String findOriginUrl(String shortUrl){
        return urlMap.get(shortUrl);
    }
}
