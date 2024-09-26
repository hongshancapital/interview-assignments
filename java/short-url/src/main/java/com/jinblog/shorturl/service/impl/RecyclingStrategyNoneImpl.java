package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.service.RecyclingStrategy;

/**
 * 不作任何回收操作
 */
public class RecyclingStrategyNoneImpl implements RecyclingStrategy {


    public RecyclingStrategyNoneImpl() {
    }

    /**
     * 响应增删改事件
     * @param key 发生事件的url
     */
    @Override
    public void handleAddEvent(String key) {
    }

    @Override
    public void handleGetEvent(String key) {
    }

    @Override
    public String popGarbage() {
        return null;
    }
}
