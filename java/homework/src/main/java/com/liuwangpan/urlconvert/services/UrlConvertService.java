package com.liuwangpan.urlconvert.services;

/**
 * @Deacription 短地址核心接口
 * @author wp_li
 **/
public interface UrlConvertService {

    /**
     * 生成短地址
     *
     * @param longUrl
     * @return
     */
    String generateShortUrl(String longUrl);

    /**
     * 获取长地址
     *
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);

    /**
     * hash 碰撞统计
     *
     * @return
     */
    Long getHashCollisionCount();
}