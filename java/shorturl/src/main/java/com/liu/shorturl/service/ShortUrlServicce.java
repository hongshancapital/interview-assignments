package com.liu.shorturl.service;

/**
 * Description： 短域名服务接口
 * Author: liujiao
 * Date: Created in 2021/11/11 18:44
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public interface ShortUrlServicce {

    /**
     * 根据短域名获取对应的长域名
     * @param shortUrl 短域名字符串
     * @return 长域名字符串
     */
    String getUrlByShortUrl(String shortUrl);

    /**
     * 根据长域名生成对应的短域名
     * @param url 长域名字符串
     * @return 对应生产的短域名字符串
     */
    String addShortUrlByUrl(String url);
}
