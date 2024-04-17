package com.example.urltransform.service;

/**
 * author  fengguangwu
 * createTime  2022/5/7
 * desc
 **/
public interface UrlTransformService {

    /**
     * 通过MD5方式获取original Url
     * @param shortUrl
     * @return original Url
     */
     String getOriginalUrl(String shortUrl);

    /**
     * 保存original
     * @param longUrl
     * @return short url
     */
    String saveOriginalUrl(String longUrl); 
}
