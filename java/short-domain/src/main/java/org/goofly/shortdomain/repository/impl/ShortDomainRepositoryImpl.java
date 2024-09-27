package org.goofly.shortdomain.repository.impl;

import org.goofly.shortdomain.config.CacheConfig;
import org.goofly.shortdomain.repository.ShortDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
@Service
public class ShortDomainRepositoryImpl implements ShortDomainRepository {

    @Autowired
    private CacheConfig cacheConfig;

    @Override
    public void save(String shortCode, String originalUrl) {
        cacheConfig.put(shortCode,originalUrl);
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return cacheConfig.get(shortCode);
    }
}
