package io.nigelwy.javaassignments.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Repository
public class InMemoryUrlRepository implements UrlRepository {

    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1_000_000L)
            .expireAfterAccess(Duration.of(1, ChronoUnit.DAYS))
            .build();

    @Override
    public void save(String shortUrl, String originUrl) {
        cache.put(shortUrl, originUrl);
    }

    @Override
    public String get(String shortUrl) {
        return cache.getIfPresent(shortUrl);
    }
}
