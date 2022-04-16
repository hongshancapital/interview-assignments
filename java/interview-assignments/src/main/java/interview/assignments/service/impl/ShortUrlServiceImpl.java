package interview.assignments.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import interview.assignments.utils.ShortUrlGenerator;
import interview.assignments.service.IShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 短域名服务实现类
 *
 * @author zhiran.wang
 * @date 2022/4/10 22:18
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    private LoadingCache<String, String> longUrlAndShortUrlCache = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(1000)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    String shortUrl = ShortUrlGenerator.generate(key);
                    shortUrlAndlongUrlCache.put(shortUrl, key);
                    return shortUrl;
                }
            });

    private Cache<String, String> shortUrlAndlongUrlCache = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(1000)
            .build();

    @Override
    public String getShortUrl(String longUrl) {
        if(StringUtils.isEmpty(longUrl)){
            return null;
        }
        try {
            return longUrlAndShortUrlCache.get(longUrl);
        } catch (ExecutionException e) {
            log.warn("获取长链接{}的短链接缓存失败", longUrl, e);
            return null;
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return shortUrlAndlongUrlCache.getIfPresent(shortUrl);
    }
}
