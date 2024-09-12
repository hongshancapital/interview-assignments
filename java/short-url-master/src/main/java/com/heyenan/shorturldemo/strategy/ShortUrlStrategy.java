package com.heyenan.shorturldemo.strategy;

/**
 * @author heyenan
 * @description 映射策略模式接口
 *
 * @date 2020/5/07
 */
public interface ShortUrlStrategy {

    /**
     * 获取短连接
     *
     * @param longUrl 长链接
     * @return 短链接
     */
    public String getShortUrl(String longUrl);

}
