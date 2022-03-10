package com.scdt.yulinfu.service;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
public interface DataStoreService {

    /**
     * 最大数量
     */
    long MAX = 64 ^ 8;

    ConcurrentHashMap<String, String> SHORT_LINK_STORE = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> LONG_LINK_STORE = new ConcurrentHashMap<>();

    /**
     * 保存数据
     * @param shortLink
     * @param longLink
     */
    default void saveData(String shortLink, String longLink) {
        if (SHORT_LINK_STORE.size() >= MAX) {
            throw new RuntimeException("已超出处理范围，请更新处理逻辑");
        }
        if (StringUtils.isEmpty(shortLink) || StringUtils.isEmpty(longLink)) {
            return;
        }
        LONG_LINK_STORE.put(shortLink, longLink);
        SHORT_LINK_STORE.put(longLink, shortLink);
    }

    /**
     * 获取短链接
     * @param longLink
     * @return
     */
    default String getShortLink(String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }
        return SHORT_LINK_STORE.get(longLink);
    }

    /**
     * 获取长链接
     * @param shortLink
     * @return
     */
    default String getLongLink(String shortLink) {
        if (StringUtils.isEmpty(shortLink)) {
            return null;
        }
        return LONG_LINK_STORE.get(shortLink);
    }

    /**
     * 获取当前映射
     * @return
     */
    long getCurrent();
}
