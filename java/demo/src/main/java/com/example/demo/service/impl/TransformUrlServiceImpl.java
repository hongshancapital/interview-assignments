package com.example.demo.service.impl;

import com.example.demo.service.TransformUrlService;
import com.example.demo.util.ShortUrlGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TransformUrlServiceImpl
 * @Description TODO
 * @Author 845627580@qq.com
 * @Version 1.0
 **/
@Service("transformUrlService")
public class TransformUrlServiceImpl implements TransformUrlService {

    private static final Logger log = LoggerFactory.getLogger(TransformUrlServiceImpl.class);
    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.timeout}")
    private Long shortUrlTimeout;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final  String KeyPrefix = "short_url_hash_md5_key_";

    private static final  String shortUrlKeyPrefix = "short_url_hash_key_";


    @Override
    public String ShortUrlTransformLongUrl(String ShortUrl) {
        String longUrl = stringRedisTemplate.opsForValue().get(shortUrlKeyPrefix + ShortUrl.substring(ShortUrl.lastIndexOf("/") + 1));
        if (org.springframework.util.StringUtils.isEmpty(longUrl)){
            log.warn("对应url {},没有找到原链接", longUrl);
            return "对应url {},没有找到原链接";
        }
        return longUrl;
    }

    //长域名转短域名并存储接口
    @Override
    public String LongUrlTransformShortUrl(String LongUrl) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        final String md5 = DigestUtils.md5Hex(LongUrl);
        String shortUrl = operations.get(KeyPrefix + md5);
        if (shortUrl == null){
            synchronized (md5.intern()){
                shortUrl = operations.get(KeyPrefix + md5);
                if (shortUrl == null){
                    String[] aResult = ShortUrlGenerator.shortUrl(LongUrl);
                    Random random=new Random();
                    int j=random.nextInt(4);//产成4以内随机数
                    log.info("短链接:"+aResult[j]);//随机取一个作为短链
                    shortUrl = aResult[j];
                    operations.set(KeyPrefix + md5, shortUrl, shortUrlTimeout, TimeUnit.DAYS);
                    operations.set(shortUrlKeyPrefix + shortUrl, LongUrl, shortUrlTimeout, TimeUnit.DAYS);
                }
            }
        }
        return shortUrl;
    }

}
