package com.example.util;

/**
 * 描述:
 * 被缓存的对象
 *
 * @author eric
 * @create 2021-07-21 4:19 下午
 */
public class CacheObject {
    /**
     * 缓存对象
     */
    private Object value;

    /**
     * 生存时间
     */
    private long ttl;

    /**
     * 构造函数
     *
     * @param value 缓存对象
     * @param ttl   生存时间毫秒
     */
    public CacheObject(Object value, long ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
