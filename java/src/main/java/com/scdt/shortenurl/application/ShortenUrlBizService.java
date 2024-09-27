package com.scdt.shortenurl.application;

/**
 * @Description 长短链转换Biz层
 * @Author chenlipeng
 * @Date 2022/3/7 2:13 下午
 */
public interface ShortenUrlBizService {

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
