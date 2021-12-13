package com.jk.shorturl.service;

/**
 * @author Jiang Jikun
 * ShortCode
 * @Description 短域名生成接口服务类
 */
public interface ShortService {

    /**
     * 根据长域名，生成短域名。
     *
     * @param longURL 长域名
     * @return 短域名
     */
    String generalShortURL(String longURL);

    /**
     * 根据短码，获取长域名
     *
     * @param shortCode 短码
     * @return 长域名/ null 表示不存在
     */
    String getLongURL(String shortCode);
}
