package com.shorturl.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by ruohanpan on 21/3/23.
 */
@Component
public class ServerVersionCache {

    static final String SERVER_VERSION_IN_USE = "server_version_in_use";

    static final Long SERVER_VERSION_EXPIRATION = 10 * 60 * 1000L;

    @Autowired
    private StringRedisTemplate template;

    public Set<String> readInUseCache() {
        return template.opsForZSet().range(SERVER_VERSION_IN_USE, 0, -1);
    }

    public void removeVersion(Long version) {
        template.opsForZSet().remove(SERVER_VERSION_IN_USE, version.toString());
    }

    public void removeExpiredVersion() {
        long current = System.currentTimeMillis();
        double min = 0, max = current - SERVER_VERSION_EXPIRATION;
        template.opsForZSet().removeRangeByScore(SERVER_VERSION_IN_USE, min, max);
    }

    public void addInUseCache(Long version) {
        template.opsForZSet().add(SERVER_VERSION_IN_USE, version.toString(), System.currentTimeMillis());
    }

    public void refreshExpiration(Long version) {
        addInUseCache(version);
    }
}
