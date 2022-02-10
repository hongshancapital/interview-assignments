package com.example.domain.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static com.example.domain.util.Constant.CONTAINER_LASTING;

@Component
public class RedisMethod4Insert {

    /**
     * 将数据存放到redis
     * @param shortDomain
     * @param longDomain
     * @return
     */
    @Cacheable(value = CONTAINER_LASTING,key = "#shortDomain")
    public String insertDomain2Redis(String shortDomain,String longDomain){
        return longDomain;
    }
}
