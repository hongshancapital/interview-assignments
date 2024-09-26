package org.demo.shortlink.service;

/**
 * @author wsq
 * @date 2022/3/26 002616:15
 * @description:
 */

public interface CacheService {
    void put(String key, String value);
    String get(String key);
}
