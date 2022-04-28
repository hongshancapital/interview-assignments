package com.web.work.service.impl;

import com.web.work.common.util.LocalCache;
import com.web.work.common.util.StringUtil;
import com.web.work.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    public DomainServiceImpl(LocalCache localCache) {
        this.localCache = localCache;
    }

    @Override
    public String createShortDomain(String fullUrl) {
        String code64 = StringUtil.generate62RadixCode(fullUrl);
        // save the mapping of domain
        Object fetchedFullUrl = localCache.get(code64);
        if (Objects.nonNull(fetchedFullUrl)) {
            return domain + code64;
        }
        localCache.put(code64, fullUrl);
        return domain + code64;
    }

    @Override
    public String getFullDomain(String shortUrl) {
        String code64 = shortUrl.replace(domain, "");
        Object fullUrl = localCache.get(code64);
        if (Objects.isNull(fullUrl)) {
            return "";
        }
        return String.valueOf(fullUrl);
    }
}

