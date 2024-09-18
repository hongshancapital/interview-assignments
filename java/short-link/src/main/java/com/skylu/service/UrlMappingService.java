package com.skylu.service;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName UrlMappingService.java
 * @Description url映射接口
 * @createTime 2022年04月22日 15:34:00
 */
public interface UrlMappingService {

    /**
     * 长域名转短域名（短域名编码）
     * @param longUrl 长域名 如：https://www.test.com/8848
     * @return 短域名编码
     */
    String longToShort(String longUrl);

    /**
     * 短域名（短域名编码）转长域名
     * @param shortUrl 短域名（域名编码） 如：yxss1sd
     * @return 长域名
     */
    String shortToLong(String shortUrl);
}
