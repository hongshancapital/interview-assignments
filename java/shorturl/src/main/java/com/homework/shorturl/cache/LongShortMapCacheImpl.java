package com.homework.shorturl.cache;

import com.homework.shorturl.model.LongShortMapModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class LongShortMapCacheImpl implements LongShortMapCache {

    @Value("${shorturl.maxSupportCapacity}")
    private int maxSupportCapacity;

    private final ConcurrentMap<String, LongShortMapModel> long2ShortCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, LongShortMapModel> short2LongCache = new ConcurrentHashMap<>();

    @Override
    public void addOrUpdate(LongShortMapModel mapModel) {
        long2ShortCache.put(mapModel.getLongUrl(), mapModel);
        short2LongCache.put(mapModel.getShortUrl(), mapModel);
    }

    @Override
    public void delete(LongShortMapModel mapModel) {
        long2ShortCache.remove(mapModel.getLongUrl());
        short2LongCache.remove(mapModel.getShortUrl());
    }

    @Override
    public Optional<LongShortMapModel> getByLong(String url) {
        return Optional.ofNullable(long2ShortCache.get(url));
    }

    @Override
    public Optional<LongShortMapModel> getByShort(String url) {
        return Optional.ofNullable(short2LongCache.get(url));
    }

    @Override
    public int getCacheSize() {
        return maxSupportCapacity;
    }

    @Override
    public int getInUsedCacheSize() {
        return long2ShortCache.size();
    }
}
