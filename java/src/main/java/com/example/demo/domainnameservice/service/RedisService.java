package com.example.demo.domainnameservice.service;

/**
 * redis service.
 *
 * @author laurent
 * @date 2021-12-11 下午2:00
 */
public interface RedisService {

    /**
     * set key and value, expires when expire is set.
     * @param key       mapping key.
     * @param value     mapping value.
     * @param expire    expire time(seconds).
     */
    void setValue(String key, String value, long expire);

    /**
     * get value by key.
     * @param key   mapping key.
     * @return      mapping value, null if not exists.
     */
    String getValue(String key);
}
