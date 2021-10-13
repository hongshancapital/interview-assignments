package com.skg.domain.demo.service.domain.impl;

import com.skg.domain.demo.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author smith skg
 * @Date 2021/10/12 10:42
 * @Version 1.0
 */
@Service
public class DomainServiceImpl implements IDomainService {

    private final String domainChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private RedisTemplate redisTemplate;

    private final String domainCacheKey = "domain:cache:key";
    private final String domainCacheValue = "domain:cache:value";

    ConcurrentHashMap<String,String> currentHashMap = new ConcurrentHashMap <>();

    @Override
    public String generateShort(String domain) {

        Object shortDomain = redisTemplate.opsForHash().get(domainCacheValue,domain);
        if(shortDomain!=null){
            return (String)shortDomain;
        }

        Integer length = ThreadLocalRandom.current().nextInt(8) + 1;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(domainChars.charAt(ThreadLocalRandom.current().nextInt(62)));
        }
        Object data = redisTemplate.opsForHash().putIfAbsent(domainCacheKey,sb.toString(),domain);
        redisTemplate.opsForHash().putIfAbsent(domainCacheValue,domain,sb.toString());
        if(!Objects.isNull(data)){//
            return generateShort(domain);
        }

        return sb.toString();
    }

    @Override
    public String getLongByShortDomain(String domain) {
        return (String)redisTemplate.opsForHash().get(domainCacheKey,domain);
    }

    @Override
    public String generateShortLocal(String domain) {
        return null;
    }

    @Override
    public String getLongByShortDomainLocal(String domain) {
        return null;
    }


}
