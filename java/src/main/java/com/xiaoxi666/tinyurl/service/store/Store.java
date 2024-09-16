package com.xiaoxi666.tinyurl.service.store;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 存储
 */
public interface Store {
    /**
     * 存储
     *
     * @param tinyPath 短链接的path
     * @param longUrl 长链接
     */
    void put(String tinyPath, String longUrl);

    /**
     * 根据短链接获取对应的长连接
     *
     * @param tinyurl 短链接
     */
    String get(String tinyurl);
}
