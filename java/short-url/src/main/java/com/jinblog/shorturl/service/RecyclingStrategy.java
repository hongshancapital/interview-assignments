package com.jinblog.shorturl.service;

public interface RecyclingStrategy {
    /**
     * 响应添加事件
     * @param key 发生的事件的url
     */
    public void handleAddEvent(String key);

    /**
     * 响应获取事件
     * @param key 发生事件的url
     */
    public void handleGetEvent(String key);

    /**
     * 弹出需要清理掉的元素
     * @return 需要清理的key
     */
    public String popGarbage();
}
