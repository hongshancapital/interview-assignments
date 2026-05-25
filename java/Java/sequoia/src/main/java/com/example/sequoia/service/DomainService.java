package com.example.sequoia.service;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xurui
 */
@Service
public class DomainService {

    /**
     * 长链接对应的短链接map
     * key：长链接
     * value：长链接
     */
    private final Map<String, String> urlMap = new HashMap<>();

    /**
     * 获取长链接对应的短链接
     * @param longLink：长链接
     * @return 短链接
     */
    public String getShortLink(String longLink) {
        if (null == longLink || longLink.length() == 0) {
            return "";
        }
        //在内存中找，找不到再创建新的
        String shortUrl = urlMap.get(longLink);
        if (null != shortUrl) {
            return shortUrl;
        }
        shortUrl = generateShortUrl(longLink);
        //存入map
        if (null != shortUrl) {
            urlMap.put(longLink, shortUrl);
        }
        return shortUrl;
    }

    /**
     * 获取短链接对应的长链接
     * @param shortLink：短链接
     * @return 长链接
     */
    public String getLongLink(String shortLink) {
        if (null == shortLink || shortLink.length() == 0) {
            return "";
        }
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals(shortLink)) {
                return entry.getKey();
            }
        }
        return "";
    }

    /**
     * 将长链接专成短链接
     * @param longLink：长链接
     * @return 短链接
     */
    public String generateShortUrl(String longLink) {
        long longLinkHash = Hashing.murmur3_32_fixed().hashString(longLink, StandardCharsets.UTF_8).padToLong();
        return Base62Utils.encodeToBase62String(Math.abs(longLinkHash));
    }

}
