package com.scdt.shortenurl.domain.service;

/**
 * @Description 长链接转换为短链的策略类
 * @Author chenlipeng
 * @Date 2022/3/7 2:16 下午
 */
public interface ShortenUrlStrategy {
    /**
     * 将长链接转换为短链接
     * @param url
     * @return
     */
    String genShortUrl(String url);
}
