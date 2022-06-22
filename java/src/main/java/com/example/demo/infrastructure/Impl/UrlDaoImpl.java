/**
 * @(#)UrlDaoImpl.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.infrastructure.Impl;

import com.example.demo.application.UrlDao;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张三
 */
@Component
public class UrlDaoImpl implements UrlDao {

    private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();

    @Override
    public void save(String key, String longUrl) {
        CACHE.put(key, longUrl);
    }

    @Override
    public String queryLongUrl(String key) {
        return CACHE.get(key);
    }
}