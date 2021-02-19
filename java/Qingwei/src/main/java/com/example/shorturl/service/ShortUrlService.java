/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.service;

/**
 * 短链接服务，缓存加数据库
 * com.example.shorturl.service shorturl
 *
 * @create mencius 2021-02-18 19:41
 */

public interface ShortUrlService {

    /**
     * 从缓存中获取fullUrl
     *
     * @param code
     * @return
     */
    public String queryLongUrl(String code);

    /**
     * 从缓存中获取code
     *
     * @param fullUrl
     * @param expirtTime
     * @return
     */
    public String genCode(String fullUrl, int expirtTime);
}
