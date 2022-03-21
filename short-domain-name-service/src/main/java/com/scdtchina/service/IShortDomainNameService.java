package com.scdtchina.service;

/**
 * @author fhzz
 *
 */
public interface IShortDomainNameService {

    /**
     * 利用短域名生成算法，将长域名压缩成短域名
     * @param longDomainName
     * @return
     */
    String generateShortDomainName(String longDomainName);

    /**
     * 利用短域名反解析算法，将短域名还原成长域名
     * @param shortDomainName
     * @return
     */
    String obtainLongDomainName(String shortDomainName);
}
