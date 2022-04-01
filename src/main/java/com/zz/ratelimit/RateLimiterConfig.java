package com.zz.ratelimit;

import java.util.concurrent.TimeUnit;

/**
 * 用于限流的注解配置
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class RateLimiterConfig {
    /**
     * 限流注解key
     */
    private String key;

    /**
     * 每秒请求限制
     */
    private int QPS;

    /**
     * 超时时间，默认不等待
     */
    private int timeout;

    /**
     * 超时时间单位，默认是毫秒
     */
    private TimeUnit timeUnit;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getQPS() {
        return QPS;
    }

    public void setQPS(int QPS) {
        this.QPS = QPS;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
