package com.adrian.service.impl;

import com.adrian.service.UrlService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 长短域名转换Service层实现
 */
@Service
public class UrlServiceImpl implements UrlService {

    /**
     * 返回的短域名前缀
     */
    private static final String SHORT_URL_PREFIX = "ur.com/";

    /**
     * 存入JVM内存Map中的短域名的key前缀
     */
    private static final String KEY_PREFIX = "short_url_hash_md5_key_";

    /**
     * 存入JVM内存Map中的长域名的key前缀
     */
    private static final String LONG_URL_KEY_PREFIX = "short_url_hash_key_";

    /**
     * 自增变量，初始值为0
     */
    private static Long serialNumberKey = 0L;

    /**
     * 用于存储短域名以及完整长域名的集合Map
     */
    private static Map<String, String> urlMap = new HashMap<>();

    /**
     * 生成短域名后缀时所用到的char数组
     */
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};

    /**
     * 长域名转换成短域名的实现方法
     *
     * @param url 完整的长域名
     * @return 短域名
     */
    @Override
    public String transformUrl(String url) {
        // 先通过MD5转换
        final String md5 = DigestUtils.md5DigestAsHex(url.getBytes(StandardCharsets.UTF_8));
        // 通过约定好的前缀和生成的MD5组成的key去urlMap中查询，如果存在，则说明该域名已经被生成过短域名，可以直接返回生成的短域名
        String shortUrl = urlMap.get(KEY_PREFIX + md5);
        if (shortUrl == null) {
            synchronized (md5.intern()) {
                shortUrl = urlMap.get(KEY_PREFIX + md5);
                // 若找不到对应数据，说明该域名还未被生成过短域名，则进行如下处理
                if (shortUrl == null) {
                    // 先将自增变量+1，保存在变量value中，再将value作为参数获取到新生成的后缀，最后在加上短域名前缀，返回该短域名
                    Long value = ++serialNumberKey;
                    String longString = compressNumber(value);
                    shortUrl = SHORT_URL_PREFIX + longString;
                    // 将生成的短域名和传入的长域名都保存在该集合中，用于下次查找
                    urlMap.put(KEY_PREFIX + md5, shortUrl);
                    urlMap.put(LONG_URL_KEY_PREFIX + shortUrl, url);
                }
            }
        }
        return shortUrl;
    }

    /**
     * 通过短域名找到完整长域名的实现方法
     *
     * @param url 短域名
     * @return 完整的长域名
     */
    @Override
    public String findOriginalUrl(String url) {
        // 直接从urlMap中通过key值取到对应的长域名，如果没有则返回空
        return urlMap.get(LONG_URL_KEY_PREFIX + url);
    }

    /**
     * 对应短域名后缀的生成方法
     *
     * @param number 递增的数值
     * @return 生成的后缀
     */
    private static String compressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}