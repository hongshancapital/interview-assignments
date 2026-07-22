package com.wenchao.jacoco.demo.storage;

/**
 * 长短链接映射存储
 *
 * @author Wenchao Gong
 * @date 2021-12-15
 */
public interface IStorage {

    /**
     * 存放时 同时存放入两个缓存中
     * @param longUrl
     * @param shortUrl
     */
    void put(String longUrl, Long shortUrl);

    /**
     * 通过短URl 获取长 URL
     * @param shortUrl
     * @return
     */
    String getLongUrl(Long shortUrl);

    /**
     * 通过长URl 获取短 URL
     * @param longUrl
     * @return
     */
    Long getShortUrl(String longUrl);

    /**
     * 递增获取下一个整数
     * @return 整数
     */
    Long nextVal();
}
