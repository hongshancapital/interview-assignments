package com.xxx.shortlink.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xxx.shortlink.commons.ShortUrlGenerator;
import com.xxx.shortlink.entity.Link;
import com.xxx.shortlink.mapper.LinkMapper;
import com.xxx.shortlink.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {

    @Value("${machine.no}")
    private String machineNo;


    private static final long SECONDS_300 = 300;

    private static final long SECONDS_600 = 600;
    private static Cache<Object, Object> cache = null;

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static {
        cache = Caffeine.newBuilder().initialCapacity(512).maximumSize(1024).expireAfterWrite(SECONDS_300, TimeUnit.SECONDS).build();
    }


    @Override
    public String save(String link) {
        if (StringUtils.isEmpty(link)) {
            return "";
        }

        String shortLink = ShortUrlGenerator.generateRandomShortUrl(machineNo);

        linkMapper.insert(shortLink, link);

        cache.put(shortLink, link);

        stringRedisTemplate.boundValueOps(shortLink).setIfAbsent(link, SECONDS_600, TimeUnit.SECONDS);

        return shortLink;
    }



    @Override
    public String get(String shortLink) {
        if (StringUtils.isEmpty(shortLink)) {
            return "";
        }

        // 先从JVM缓存中获取
        Object o = cache.getIfPresent(shortLink);
        if (o == null) {
            // 未从JVM中获取时，从Redis中获取
            String originalLink = stringRedisTemplate.boundValueOps(shortLink).get();
            if(StringUtils.isEmpty(originalLink)) {
                Link link = linkMapper.selectByShortLink(shortLink);
                if(link == null) {
                    return "";
                }
                originalLink = link.getOriginalLink();
                cache.put(shortLink, originalLink);
                stringRedisTemplate.boundValueOps(shortLink).setIfAbsent(originalLink, 600, TimeUnit.SECONDS);
            }
            cache.put(shortLink, originalLink);
            return originalLink;
        }
        return (String) o;

    }
}
