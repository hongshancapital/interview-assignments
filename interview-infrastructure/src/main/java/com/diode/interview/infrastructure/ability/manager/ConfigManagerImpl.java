package com.diode.interview.infrastructure.ability.manager;

import com.diode.interview.domain.ability.ConfigManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Component
public class ConfigManagerImpl implements ConfigManager {
    @Value("${caffeine.cache.expire}")
    private int caffeineCacheExpireSecs;
    @Value("${caffeine.cache.max.size}")
    private long caffeineCacheMaxSize;

    public int getCaffeineCacheExpireSecs() {
        return caffeineCacheExpireSecs;
    }

    public long getCaffeineCacheMaxSize() {
        return caffeineCacheMaxSize;
    }
}
