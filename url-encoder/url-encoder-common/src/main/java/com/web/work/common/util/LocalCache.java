package com.web.work.common.util;

import java.util.Objects;

import com.web.work.common.constants.CacheConstant;
import com.web.work.common.exception.BadRequestException;
import com.web.work.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * local cache class
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 11:23 AM
 */
@Component
public class LocalCache {

    private final CacheManager cacheManager;

    @Autowired
    public LocalCache(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Cache getLocalCache() {
        Cache cache = cacheManager.getCache(CacheConstant.CACHE_10SECS);
        if (Objects.isNull(cache)) {
            throw new BadRequestException(ErrorCode.EXCEPTION_ERROR);
        }
        return cache;
    }
}
