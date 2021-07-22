package com.hello.tinyurl.service;

/**
 * @author: Shuai
 * @date: 2021-7-5 21:54
 * @description:
 */
public interface TinyUtlService {

    String saveOriginalUrl(String originalUrl) throws Exception;

    String getOriginalUrl(String shortUrl) throws Exception;

}
