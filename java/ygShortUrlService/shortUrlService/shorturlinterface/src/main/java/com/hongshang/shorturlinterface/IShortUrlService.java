package com.hongshang.shorturlinterface;

/**
 * @author: yangguowen
 * @createDate: 2021/12/16
 * @description: 用于地址转换的类，长地址 得到短地址，  或 短地址 得到 长地址
 */
public interface IShortUrlService {

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl String 长域名
     * @return String 短域名
     */
    public String transformToShortUrl(String longUrl);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     *
     * @param shortUrl String 短域名
     * @return String  长域名
     */
    public String getLongUrlByShortUrl(String shortUrl);

}
