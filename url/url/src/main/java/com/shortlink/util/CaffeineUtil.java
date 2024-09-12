package com.shortlink.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Objects;

/**
 * 本地缓存工具类
 */
public class CaffeineUtil {

    /**
     * 定义缓存最大个数
     */
    private static final int CACHE_MAXIMUM_SIZE = 1_000_000;

    /**
     * 本地缓存最长停留时间
     */
    private static final int CACHE_MAX_DAY = 1;
    private static Cache<String, Object> cache = buildLocalCacheAndExpire();

    /**
     * 设置softValues 避免内存溢出 真实生产环境中 存在持久化层 需要观察内存情况
     * @param <k>
     * @param <v>
     * @return
     */
    private static <k, v> Cache<k, v> buildLocalCacheAndExpire() {
        return Caffeine.newBuilder().maximumSize(CACHE_MAXIMUM_SIZE)
                .expireAfterAccess(Duration.ofDays(CACHE_MAX_DAY))
                .softValues().build();
    }

    public static void put(String key, Object value) {
        if (StringUtils.isNotEmpty(key) || Objects.nonNull(value)) {
            cache.put(key, value);
        }
    }

    public static Object get(String key) {
        return StringUtils.isEmpty(key) ? null : cache.getIfPresent(key);
    }

    public static void del(String key) {
        if (Objects.nonNull(get(key))) {
            cache.invalidate(key);
        }
    }

}
