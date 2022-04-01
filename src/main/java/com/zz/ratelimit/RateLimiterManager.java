package com.zz.ratelimit;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 限流的管理类，管理所有的资源的限流处理
 *
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
@Component
public class RateLimiterManager {
    /**
     * 保存所有的限流资源
     */
    private ConcurrentHashMap<String, RateLimiter> allRateLimiterMap = new ConcurrentHashMap<>();

    /**
     * 注册新的限流资源
     *
     * @param config
     */
    public void register(RateLimiterConfig config) {
        //合法情况下进行注册
        if (isValid(config)) {
            allRateLimiterMap.putIfAbsent(config.getKey(), new TokenRateLimiter(config.getQPS(), config.getTimeout(), config.getTimeUnit()));
        }
    }

    /**
     * 限流尝试处理
     *
     * @return
     */
    public boolean tryAcquire(RateLimiterConfig config) {
        RateLimiter rateLimiter = allRateLimiterMap.get(config.getKey());
        if (rateLimiter == null) {
            register(config);
        }
        rateLimiter = allRateLimiterMap.get(config.getKey());
        return rateLimiter.tryAcquire();
    }

    /**
     * 限流配置合法
     *
     * @param config
     * @return
     */
    public boolean isValid(RateLimiterConfig config) {
        return config.getKey() != null && config.getQPS() > RateLimiterAnno.NOT_LIMITED;
    }
}
