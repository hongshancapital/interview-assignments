package com.evan.sdn.dto;

import com.evan.sdn.entity.DomainNameMapping;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
public interface DomainNameMappingDao {

    /**
     * 保存域名映射
     * @param domainNameMapping 域名映射
     */
    void save(DomainNameMapping domainNameMapping);

    /**
     * 根据短域名获得长域名
     * @param shortDomainName 短域名
     * @return 长域名
     */
    String getLongDomainName(String shortDomainName);

    /**
     * 根据长域名获得短域名
     * @param longDomainName 长域名
     * @return 短域名
     */
    String getShortDomainName(String longDomainName);
}
