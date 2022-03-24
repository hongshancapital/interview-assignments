package com.shorts.url.service.generator;

import com.shorts.url.service.generator.cache.LruCache;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 短链接生成器抽象类
 * 内部持有两个缓存：
 * cache: key=短链接 value=长链接
 * lruCache: key=长链接 value=短链接，大小500，可以保证最近使用的长链接重复生成的短链接不变
 * </p>
 *
 * @author WangYue
 * @date 2022/3/24 10:20
 */
public abstract class AbstractGenerator implements Generator {

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final LruCache lruCache = new LruCache(500);

    @Override
    public String generate(String longUrl) {
        // 根据长链接从 LRU 缓存中查询，最近是否生成过
        // 如果有，则直接返回
        String s = lruCache.get(longUrl);
        if (StringUtils.isNotBlank(s)) {
            return s;
        }

        // 没有生成过，重新生成，并保存在缓存中
        s = doGenerate(longUrl);
        cache.put(s, longUrl);
        lruCache.put(longUrl, s);
        return s;
    }

    @Override
    public String get(String shortUrl) {
        return cache.get(shortUrl);
    }

    /**
     * 具体生成短链接的方法
     *
     * @param longUrl 长链接
     * @return -
     */
    public abstract String doGenerate(String longUrl);
}
