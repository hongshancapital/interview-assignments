package com.zz.ratelimit;

import java.util.concurrent.TimeUnit;

/**
 * 限流公共类
 *
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public abstract class RateLimiter {
    /**
     * 限制的qps
     */
    protected int QPS;
    /**
     * 限制超时时间
     */
    protected int timeout;
    /**
     * 限制超时时间单位
     */
    protected TimeUnit timeUnit;

    public RateLimiter(int QPS, int timeout, TimeUnit timeUnit) {
        this.QPS = QPS;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    /**
     * 获取资源
     *
     * @return
     */
    public abstract boolean tryAcquire();
}
