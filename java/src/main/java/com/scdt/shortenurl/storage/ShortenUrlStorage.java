package com.scdt.shortenurl.storage;

/**
 * @Description 短链转换服务Storage层
 * @Author chenlipeng
 * @Date 2022/3/7 2:16 下午
 */
public interface ShortenUrlStorage {

    /**
     * 保存长短链关系
     * @param originalUrl
     * @return
     */
    void save(String originalUrl, String shortenUrl);

    /**
     * 根据短链获取长链
     * @param shortenUrl
     * @return
     */
    String get(String shortenUrl);

}
