package interview.shorturl.service;

import interview.shorturl.dao.po.ShortUrlInfo;

/**
 * @author: ZOUFANQI
 **/
public interface ShortUrlService {

    /**
     * 长地址转短地址（这里要添加事务）
     *
     * @param realUrl      长地址
     * @param expireSecond 失效时长（秒）
     * @return 转换信息
     */
    ShortUrlInfo convertUrl(String realUrl, Integer expireSecond);

    /**
     * 获取转换信息
     *
     * @param shortUrl 短地址
     * @return 转换信息
     */
    ShortUrlInfo getInfoByShortUrl(String shortUrl);
}
