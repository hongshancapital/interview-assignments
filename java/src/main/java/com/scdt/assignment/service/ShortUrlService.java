package com.scdt.assignment.service;

import com.scdt.assignment.controller.bo.ShortUrlBo;

/**
 * @title ShortUrlService.java
 * @description
 * @author
 * @date 2022-04-15 17:10:52
 */
public interface ShortUrlService {

    /**
     * 创建短链接
     *
     * @param lUrl
     * @return
     */
    public ShortUrlBo create(String longUrl);

    /**
     * 获取长链接
     *
     * @param sUrl
     * @return
     */
    public ShortUrlBo query(String shortUrl);
}
