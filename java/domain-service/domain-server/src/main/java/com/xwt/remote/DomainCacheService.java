package com.xwt.remote;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 域名映射服务
 *
 * @author : xiwentao
 */
public class DomainCacheService {
    /**
     * 域名映射缓存
     */
    private static ConcurrentMap<String, String> domainMap = new ConcurrentHashMap<>();

    private DomainCacheService() {
    }

    /**
     * 域名映射保存
     *
     * @param shortUrl 短域名
     * @param longUrl  长域名
     * @date: 2021-07-21
     * @return: void
     */
    public static void put(String shortUrl, String longUrl) {
        domainMap.put(shortUrl, longUrl);
    }

    /**
     * 长域名查询，如果不存在返回 ""
     *
     * @param shortUrl 短域名
     * @date: 2021-07-21
     * @return: java.lang.String
     */
    public static String get(String shortUrl) {
        if (domainMap.containsKey(shortUrl)) {
            return domainMap.get(shortUrl);
        }
        return StringUtils.EMPTY;
    }
}
