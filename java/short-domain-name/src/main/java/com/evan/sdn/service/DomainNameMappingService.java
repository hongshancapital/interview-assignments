package com.evan.sdn.service;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
public interface DomainNameMappingService {

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longDomainName 长域名信息
     * @return 短域名信息
     */
    String save(String longDomainName);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param shortDomainName 短域名信息
     * @return 长域名信息
     */
    String query(String shortDomainName);
}
