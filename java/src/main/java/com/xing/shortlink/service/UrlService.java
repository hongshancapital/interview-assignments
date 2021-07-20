package com.xing.shortlink.service;

import com.xing.shortlink.domain.http.*;
import com.xing.shortlink.domain.http.request.CreateShortUrlRequest;
import com.xing.shortlink.domain.http.request.QueryOriginalUrlRequest;
import com.xing.shortlink.domain.http.response.CreateShortUrlResponse;
import com.xing.shortlink.domain.http.response.QueryOriginalUrlResponse;

/**
 * 短链接核心服务接口定义
 *
 * @Author xingzhe
 * @Date 2021/7/17 23:00
 */
public interface UrlService {

    /**
     * 原长链接地址生成短链接
     *
     * @param request 原始链接封装
     * @return 短链接返回结果
     */
    Result<CreateShortUrlResponse> createShortUrl(CreateShortUrlRequest request);

    /**
     * 短链接查询原长链接
     *
     * @param request 短链接封装
     * @return 原始链接返回结果
     */
    Result<QueryOriginalUrlResponse> queryOriginalUrl(QueryOriginalUrlRequest request);
}
