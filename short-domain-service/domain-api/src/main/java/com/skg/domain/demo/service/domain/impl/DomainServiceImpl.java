package com.skg.domain.demo.service.domain.impl;

import com.skg.domain.demo.anno.ErrorCode;
import com.skg.domain.demo.exception.DomainException;
import com.skg.domain.demo.service.domain.IDomainService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DomainServiceImpl implements IDomainService {

    private final String domainChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private RedisTemplate redisTemplate;

    private final String domainCacheKey = "domain:cache:key";
    private final String domainCacheValue = "domain:cache:value";
    //本地存储 长短域名合适
    ConcurrentHashMap<String,String> currentHashMap = new ConcurrentHashMap <>();

    @Override
    public String generateShort(String domain) {
        //先查询redis 若存在直接返回
        Object shortDomain = redisTemplate.opsForHash().get(domainCacheValue,domain);
        if(shortDomain!=null){
            return (String)shortDomain;
        }
        //随机获取短域名
        Integer length = ThreadLocalRandom.current().nextInt(8) + 1;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(domainChars.charAt(ThreadLocalRandom.current().nextInt(62)));
        }
        //查询添加短域名到redis 若当前短域名对应的长域名已存在 递归调用 返回短域名
        Object data = redisTemplate.opsForHash().putIfAbsent(domainCacheKey,sb.toString(),domain);
        redisTemplate.opsForHash().putIfAbsent(domainCacheValue,domain,sb.toString());
        if(!Objects.isNull(data)){//
            return generateShort(domain);
        }

        return sb.toString();
    }

    @Override
    public String getLongByShortDomain(String domain) {
        Object obj = redisTemplate.opsForHash().get(domainCacheKey,domain);
        if(Objects.isNull(obj)){
            log.info("长域名{}对应的短域名不存在",domain);
            throw new DomainException(ErrorCode.DOMAIN_NOT_EXIST.getMessage());
        }
        return (String)obj;
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
