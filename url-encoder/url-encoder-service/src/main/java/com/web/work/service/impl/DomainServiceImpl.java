package com.web.work.service.impl;

import java.util.Objects;

import com.web.work.common.util.LocalCache;
import com.web.work.common.util.StringUtil;
import com.web.work.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Component;

/**
 * domain service
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/26 7:39 PM
 */
@Component
public class DomainServiceImpl implements DomainService {

    @Value("${domain}")
    private String domain;

    private final LocalCache localCache;

    @Autowired
    public DomainServiceImpl(LocalCache localCache) {this.localCache = localCache;}

    @Override
    public String createShortDomain(String fullUrl) {
        String code64 = StringUtil.generate62RadixCode(fullUrl);
        // save the mapping of domain
        Cache cache = localCache.getLocalCache();
        cache.put(code64, fullUrl);
        return domain + code64;
    }

    @Override
    public String getFullDomain(String shortUrl) {
        String code64 = shortUrl.replace(domain, "");
        Cache cache = localCache.getLocalCache();
        ValueWrapper valueWrapper = cache.get(code64);
        if (Objects.isNull(valueWrapper) || Objects.isNull(valueWrapper.get())) {
            return "";
        }
        return String.valueOf(valueWrapper.get());
    }
}

