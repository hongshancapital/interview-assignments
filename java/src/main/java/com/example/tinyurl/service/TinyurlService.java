package com.example.tinyurl.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 短网址转换业务类
 */
@Service
public class TinyurlService {
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> reverseMap = new HashMap<>();
    private static final int CODE_LENGTH = 8;
    private static final String RANDOM_POOL = "0123456789abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 1.使用Map缓存URL和tinyURL的映射关系
    // 2.为了避免缓存大量相同的URL，使用双map做校验
    // 3.使用6位62进制数字表达tinyURL，后续如果容量不够，可以扩展到更大的进制数
    // 4.安全考虑，避免从外部猜测URL缓存数量，tinyURL不适合使用自增数，改成使用随机数
    // 5.为什么不直接取URL的hash值？因为hash值不未知，不可靠
    // 6.双map缓存了2份数据，内存开销有点大，暂时没想到更好的办法
    public String encode(String longUrl) {
        if (reverseMap.containsKey(longUrl)) {
            return reverseMap.get(longUrl);
        }

        StringBuilder sb = new StringBuilder();
        while (true) {
            for (int i=0; i<CODE_LENGTH; i++) {
                int pos = (int) (Math.random() * RANDOM_POOL.length());
                sb.append(RANDOM_POOL.charAt(pos));
            }

            if (map.containsKey(sb.toString())) {
                continue;
            } else {
                break;
            }
        }
        String key = sb.toString();
        map.put(key, longUrl);
        reverseMap.put(longUrl, key);
        return key;
    }

    public String decode(String shortUrl) {
        return map.get(shortUrl);
    }
}
