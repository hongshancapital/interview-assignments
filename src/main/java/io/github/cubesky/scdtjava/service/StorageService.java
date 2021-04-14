package io.github.cubesky.scdtjava.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;

/**
 * 存储服务
 */
@Component
public class StorageService {
    //实际存储的映射
    private final BiMap<String, String> urlStorageMap = HashBiMap.create();

    @Resource
    private UrlGenerateService urlGenerateService;

    /**
     * 缩短网址
     * @param longUrl 长地址
     * @return 如果成功缩短，返回 缩短 hash，如果未成功返回 null
     */
    public String longUrlToShortUrl(String longUrl) {
        //获取一个 Map 的反转 View 查询是否存在
        String shortUrl = urlStorageMap.inverse().get(longUrl);
        //如果不存在就缩短
        if (shortUrl == null) {
            synchronized (this) {
                shortUrl = urlGenerateService.getShortUrl();
                if (shortUrl == null) return null;
                urlStorageMap.put(shortUrl, longUrl);
            }
        }
        return shortUrl;
    }

    /**
     * 查询长链接
     * @param shortUrl 短链接 hash
     * @return 长链接地址 或 null
     */
    public String shortUrlToLongUrl(String shortUrl) {
        return urlStorageMap.get(shortUrl);
    }

    public void reset() {
        urlStorageMap.clear();
    }
}
