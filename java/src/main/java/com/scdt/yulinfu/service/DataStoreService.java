package com.scdt.yulinfu.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
public interface DataStoreService {

    ConcurrentHashMap<String, String> SHORT_LINK_STORE = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> LONG_LINK_STORE = new ConcurrentHashMap<>();

    /**
     * 保存数据
     * @param shortLink
     * @param longLink
     */
    default void saveData(String shortLink, String longLink) {
        LONG_LINK_STORE.put(shortLink, longLink);
        SHORT_LINK_STORE.put(longLink, shortLink);
    }

    /**
     * 获取短链接
     * @param longLink
     * @return
     */
    default String getShortLink(String longLink) {
        return SHORT_LINK_STORE.get(longLink);
    }

    /**
     * 获取长链接
     * @param shortLink
     * @return
     */
    default String getLongLink(String shortLink) {
        return LONG_LINK_STORE.get(shortLink);
    }
}
