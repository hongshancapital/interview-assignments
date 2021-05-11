package com.example.shortlink.infrastructure.repository;

import com.example.shortlink.domain.entity.ShortLink;
import com.example.shortlink.domain.repository.ShortLinkRepository;
import com.example.shortlink.infrastructure.common.Constants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MemoryShortLinkRepository implements ShortLinkRepository {

    private Cache<String, String> cahceBuilder = CacheBuilder.newBuilder()
            .expireAfterAccess(Constants.CACHE_EXPIRE_DAYS, TimeUnit.DAYS).maximumSize(Constants.CACHE_MAX_SIZE)
            .build();

    public void saveShortLink(ShortLink shortLink) {
        cahceBuilder.put(shortLink.getShortLinkKey(), shortLink.getLongLink());
    }

    public String getSourceLinkByShortLinkKey(String shortLinkKey) {
        return cahceBuilder.getIfPresent(shortLinkKey);
    }


}
