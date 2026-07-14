package com.zm.demo.service;

/**
 * @ClassName UrlService
 * @Author zhaomin
 * @Date 2021/10/29 17:20
 **/
public interface UrlService {

    /**
    * 接受长域名信息，返回短域名信息
    * @Param: longUrl
    * @return: java.lang.String
    * @Author: zhaomin
    * @Date: 2021/10/29 17:20
    */
    public String shortUrl(String longUrl);

    /**
    * 接受短域名信息，返回长域名信息
    * @Param: shortUrl
    * @return: java.lang.String
    * @Author: zhaomin
    * @Date: 2021/10/29 17:20
    */
    public String longUrl(String shortUrl);
}
