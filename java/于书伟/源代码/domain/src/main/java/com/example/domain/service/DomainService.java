package com.example.domain.service;

import com.example.domain.util.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

    @Autowired
    RedisMethod4Insert redisMethod4Insert;

    /**
     * 将长域名插入到redis
     * @return
     */
    public String saveDomainMethod(String longDomain){
        String shortDomain = PublicUtil.generateShortUuid();
        redisMethod4Insert.insertDomain2Redis(shortDomain,longDomain);
        return shortDomain;
    }



}
