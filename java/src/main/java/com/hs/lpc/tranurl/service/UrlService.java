package com.hs.lpc.tranurl.service;

/**
 * 获取短链服务接口
 * @author Li Pengcheng
 */
public interface UrlService {

    /**
     * 根据长链接获取短链接
     * @param longUrl 长链接
     * @return 短链接
     */
    String getShortUrl(String longUrl);

    /**
     * 根据短链接获取长链接
     * @param shortUrl 短链接
     * @return 长链接
     */
    String getLongUrl(String shortUrl);


}
