package com.service;
/*
* 长短域名转换service
* */
public interface DomainConversionService {

    /**
     * 功能描述: 长域名获取长域名
     * @return java.lang.String
     * Create on 2021/10/10
     * @author liangjiangwei
     */
    public String getShortDomainUrl(String longUrl) throws Exception;

    /**
     * 功能描述: 短域名获取长域名
     * @return java.lang.String
     * Create on 2021/10/10
     * @author liangjiangwei
     */
    public String getLongDomainUrl(String shortUrl) throws Exception;
}
