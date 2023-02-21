package com.david.urlconverter.service.dubbo;

/**
 * short url long url mapping storage service
 */
public interface IUrlConverterStorageSOAService {

    /**
     * 根据短域名，查询长域名
     * @param shortUrl
     * @return
     */
    public String retrieveLongUrl(String shortUrl);

    /**
     * 根据长域名，查询短域名
     * @param longUrl
     * @return
     */
    public String queryShortUrl(String longUrl);

    /**
     * 保存长短域名的映射
     * @param shortUrl
     * @param longUrl
     */
    public void storeUrlMapping(String shortUrl, String longUrl);


}
