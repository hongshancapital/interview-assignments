package com.domain.url.service;

import com.domain.url.exception.ServiceException;
import com.domain.url.web.data.UrlReq;

/**
 * 短链接服务接口
 */
public interface UrlService {
    /**
     * 短链接存储接口：接受长域名信息，返回短链接信息
     *
     * @param req 请求信息
     * @return 短链接
     * @throws ServiceException 长链接地址非法时抛出
     */
    String shorten(UrlReq req) throws ServiceException;

    /**
     * 短链接读取接口：接受短链接信息，返回长链接信息
     *
     * @param shortUrl 短链接
     * @return 长链接
     * @throws ServiceException 短链接不存在时抛出
     */
    String original(String shortUrl) throws ServiceException;
}
