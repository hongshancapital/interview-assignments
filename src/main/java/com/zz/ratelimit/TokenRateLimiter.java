package com.zz.ratelimit;

import java.util.concurrent.TimeUnit;

/**
 * 令牌限流算法
 *
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class TokenRateLimiter extends RateLimiter {
    private com.google.common.util.concurrent.RateLimiter rateLimiter;

    public TokenRateLimiter(int QPS, int timeout, TimeUnit timeUnit) {
        super(QPS, timeout, timeUnit);
        rateLimiter = com.google.common.util.concurrent.RateLimiter.create(QPS);
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire(timeout, timeUnit);
    }
}
