package com.xxw.domain.service.impl;


import com.xxw.domain.service.LongUrlService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service("longUrlService")
public class LongUrlServiceImpl implements LongUrlService {

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    private static final  String SHORT_URL_KEY_PREFIX = "short_url_hash_key_";

    private static final Logger log = LoggerFactory.getLogger(LongUrlServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String longUrl(String url) {

        String longUrl = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY_PREFIX + url.replace(shortUrlPrefix,""));
        if (StringUtils.isBlank(longUrl)){
            log.warn("对应url {},没有找到原链接", url);
            longUrl = "对应url {},没有找到原链接";
        }
        return longUrl;
    }
}
