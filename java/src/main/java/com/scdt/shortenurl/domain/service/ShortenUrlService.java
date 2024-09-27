package com.scdt.shortenurl.domain.service;

/**
 * @Description 短链转换服务领域层
 * @Author chenlipeng
 * @Date 2022/3/7 2:16 下午
 */
public interface ShortenUrlService {

    /**
     * 将长链转换成短链
     * @param originalUrl
     * @return
     */
    String genShortenUrl(String originalUrl);

    /**
     * 根据短链获取原链
     * @param shortenUrl
     * @return
     */
    String getOriginalUrl(String shortenUrl);

}
