package com.demo.service.impl;

import com.demo.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author syd
 * @description
 * @date 2022/1/12
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    @Autowired
    CacheManager cacheManager;

    @Override
    public String get(String key) {
        Cache demoCache = cacheManager.getCache("demoCache");
        return demoCache.get(key) == null ? null : (String) demoCache.get(key).getObjectValue();
    }

    @Override
    public void set(String key, String value) {
        Cache demoCache = cacheManager.getCache("demoCache");
        demoCache.put(new Element(key, value));
    }
}
