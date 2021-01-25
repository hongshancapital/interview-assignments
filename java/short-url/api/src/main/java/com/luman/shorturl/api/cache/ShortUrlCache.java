package com.luman.shorturl.api.cache;

import com.luman.shorturl.api.model.ShortUrl;
import com.luman.shorturl.common.redis.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ShortUrlCache {

    private static final String CODE_SHORT_URL="CODE_SHORT_URL";
    private static final String CODE_ID="CODE_ID";
    private static final String ID_CODE="ID_CODE";
    private static final String URL_SHORT_URL="URL_SHORT_URL";
    @Autowired
    RedisTemplate<String, Serializable> redis;

    /**
     * 缓存短连接
     * @param shortUrl
     */
    public void save(ShortUrl shortUrl){
        String codeShortUrlKey = CacheKeyGenerator.generateKey(CODE_SHORT_URL,shortUrl.getCode());
        String codeIdKey = CacheKeyGenerator.generateKey(CODE_ID,shortUrl.getCode());
        String idCodeUrlKey = CacheKeyGenerator.generateKey(ID_CODE,shortUrl.getId());
        redis.opsForValue().set(codeShortUrlKey,shortUrl);
        redis.opsForValue().set(codeIdKey,shortUrl.getId());
        redis.opsForValue().set(idCodeUrlKey,shortUrl.getCode());


        String urlShortUrlKey = CacheKeyGenerator.generateKey(URL_SHORT_URL,shortUrl.getUrl(),shortUrl.getExpireTime());
        redis.opsForValue().set(urlShortUrlKey,shortUrl.getCode());
        Long expireDays;
        if((expireDays=shortUrl.getExpireDays())>0){
            redis.expire(codeShortUrlKey,expireDays,TimeUnit.DAYS);
            redis.expire(codeIdKey,expireDays,TimeUnit.DAYS);
            redis.expire(idCodeUrlKey,expireDays,TimeUnit.DAYS);
            redis.expire(urlShortUrlKey,expireDays,TimeUnit.DAYS);
        }


    }

    /**
     * 根据编码获取原链接
     * @param code 编码
     * @return 链接
     */
    public ShortUrl getUrl(String code){
        String codeShortUrlKey = CacheKeyGenerator.generateKey(CODE_SHORT_URL,code);
        return (ShortUrl) redis.opsForValue().get(codeShortUrlKey);
    }


    /**
     * 根据id获取长连接
     * @param id id
     * @return 长连接
     */
    public ShortUrl getUrl(long id){
        String idCodeKey = CacheKeyGenerator.generateKey(ID_CODE,id);
        String code = (String) redis.opsForValue().get(idCodeKey);
        return getUrl(code);
    }

    /**
     * 获取短连接编码
     * @param url 长连接
     * @param expireTime 过期时间
     * @return 编码
     */
    public String getShortUrl(String url, Date expireTime){
        int expire = -1;
        if(expireTime!=null){
         expire =(int)TimeUnit.MILLISECONDS.toDays(expireTime.getTime());
        }
        String urlShortUrlKey = CacheKeyGenerator.generateKey(URL_SHORT_URL,url,expire);
        return  (String) redis.opsForValue().get(urlShortUrlKey);
    }

}
