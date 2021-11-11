package com.lynnhom.sctdurlshortservice.service;

import com.lynnhom.sctdurlshortservice.model.dto.UrlDto;
import com.lynnhom.sctdurlshortservice.model.dto.UrlResponseDto;

/**
 * @description: 短链接服务接口
 * @author: Lynnhom
 * @create: 2021-10-28 10:27
 **/
public interface ShortUrlService {

    /**
     * 生成短链接
     * @return
     */
    UrlResponseDto create(UrlDto url);


    /**
     * 根据短链接获取原链接
     * @param shortUrl
     * @return
     */
    String getOriginalUrl(String shortUrl);
}
