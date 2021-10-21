package com.example.assignment.service.impl;

import com.example.assignment.Exception.ShortCodeUseOutException;
import com.example.assignment.common.LRUCache;
import com.example.assignment.service.CodeService;
import com.example.assignment.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    private LRUCache<String, String> LRU;

    @Value("${short-url.max.url.cacheSize:1000}")
    private int cacheSize;

    @Resource
    private CodeService codeService;

    @PostConstruct
    public void initLRUCache() {
        LRU = new LRUCache<>(cacheSize);
    }

    @Override
    public String generate(String originalUrl) throws ShortCodeUseOutException {
        String shortCode = LRU.getKeyByValue(originalUrl);
        if (null == shortCode) {
            shortCode = codeService.generateCode();
            LRU.put(shortCode, originalUrl);
            log.info("LRU size is {}", LRU.size());
        }
        return shortCode;
    }

    @Override
    public String parse(String shortUrl) {
        return LRU.get(shortUrl);
    }
}
