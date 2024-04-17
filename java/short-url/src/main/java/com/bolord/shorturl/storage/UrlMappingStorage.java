package com.bolord.shorturl.storage;

public interface UrlMappingStorage {

    /**
     * 根据 ID 获取一个 URL 映射
     * @param id Base62ID
     * @return 原始的URL
     */
    String get(String id);

    /**
     * 向存储中放置一个 URL 映射
     * @param id Base62ID
     * @param url 原始的URL
     */
    void set(String id, String url);

    /**
     * 根据 ID 删除存储中的映射
     * @param id Base62ID
     */
    void remove(String id);

    /**
     * 获取存储中的映射数量
     * @return 存储中的映射数量
     */
    int size();
}
