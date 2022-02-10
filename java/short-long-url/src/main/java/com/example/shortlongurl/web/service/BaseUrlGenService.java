package com.example.shortlongurl.web.service;

import com.example.shortlongurl.framework.exception.CustomException;
import com.example.shortlongurl.framework.utils.Base62Utils;
import com.example.shortlongurl.framework.utils.StringUtils;
import com.example.shortlongurl.web.model.ShortLongUrlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class BaseUrlGenService implements IUrlGenService {
    private static final  String LongUrlKeyPrefix = "long_url_hash_key_";
    private static final  String ShortUrlKeyPrefix = "short_url_hash_key_";

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.userCacheName}")
    private String userCacheName;
    @Value("${short.url.timeout}")
    private Long shortUrlTimeout;
    @Autowired
    private IDKeyGenerator idKeyGenerator;
    @Autowired
    private CacheManager cacheManager;
    private Cache cache;

    @PostConstruct
    public void init(){
        this.cache = cacheManager.getCache(userCacheName);
    }


    public ShortLongUrlModel getShortUrl(String longUrl) {
        if(StringUtils.isEmpty(longUrl)) return null;
        final String longKey = genLongKey(longUrl);

        ShortLongUrlModel shortLongUrlModel = cacheGet(LongUrlKeyPrefix + longKey,ShortLongUrlModel.class);
        if (null == shortLongUrlModel){
            synchronized (longKey.intern()){
                shortLongUrlModel = cacheGet(LongUrlKeyPrefix + longKey,ShortLongUrlModel.class);
                if (null == shortLongUrlModel){
                    String shortStr = genShortStr();
                    String shortUrl = shortUrlPrefix + shortStr;
                    shortLongUrlModel = ShortLongUrlModel.builder()
                            .longUrl(longUrl)
                            .shortUrl(shortUrl)
                            .key(longKey)
                            .expire(LocalDateTime.now().plusDays(shortUrlTimeout)
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant().toEpochMilli())
                            .build();
                    cachePut(LongUrlKeyPrefix + longKey, shortLongUrlModel);
                    cachePut(ShortUrlKeyPrefix + shortStr, shortLongUrlModel);
                }
            }
        }
        return shortLongUrlModel;
    }

    protected <T> void cachePut(String key, T value){
        this.cache.put(key,value);
    }

    protected <T> T cacheGet(String key,Class<T> clazz){
        return this.cache.get(key,clazz);
    }

    protected void cacheRemove(String key){
        this.cache.evict(key);
    }


    protected  String genLongKey(String longUrl){
        return String.valueOf(longUrl.hashCode());
    };

    protected String genShortStr(){
        return Base62Utils.encodeBase62(idKeyGenerator.getNextId());
    }

    @Override
    public ShortLongUrlModel getLongUrl(String shortUrl) {
        String shortStr = shortUrl.substring(shortUrlPrefix.length());
        final ShortLongUrlModel shortLongUrlModel = cacheGet(ShortUrlKeyPrefix + shortStr,ShortLongUrlModel.class);
        if(null == shortLongUrlModel){
            throw new CustomException("获取长链接失败");
        }
        if(isExpire(shortLongUrlModel.getKey())){
            cacheRemove(ShortUrlKeyPrefix + shortStr);
            cacheRemove(shortLongUrlModel.getKey());
            throw new CustomException("长链接已失效");
        }
        return shortLongUrlModel;
    }

    @Override
    public boolean isExpire(String key) {
        ShortLongUrlModel shortLongUrlModel = cacheGet(key, ShortLongUrlModel.class);
        if(null == shortLongUrlModel) return false;
        return shortLongUrlModel.isExpire();
    }
}
