package com.example.app.service;

import com.example.app.common.exception.ModuleException;

/**
 * 短域名操作类接口
 *
 * @author voidm
 * @date 2021/9/18
 */
public interface DomainService {

    /**
     * 根据完整域名生成短域名
     * @param fullUrl
     * @return
     */
    String generateShortUrl(String fullUrl) throws ModuleException;

    /**
     * 根据短域名获取完整域名
     * @param shortUrl
     * @return
     */
    String parseWithShortUrl(String shortUrl) throws ModuleException;
}
