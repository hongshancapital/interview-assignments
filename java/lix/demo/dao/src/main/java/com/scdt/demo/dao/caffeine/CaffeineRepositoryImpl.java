package com.scdt.demo.dao.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CaffeineRepositoryImpl implements CaffeineRepository{

    private final Cache<String, Object> cache;

    @Autowired
    public CaffeineRepositoryImpl(Cache<String, Object> cache) {
        this.cache = cache;
    }


    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        Object obj = cache.getIfPresent(key);
        return Optional.ofNullable(obj).map(o -> cast(o, clazz));
    }

    private <T> T cast(Object obj, Class<T> clazz) {
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        return null;
    }
}
