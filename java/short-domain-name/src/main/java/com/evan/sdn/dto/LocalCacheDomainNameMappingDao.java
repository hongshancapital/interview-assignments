package com.evan.sdn.dto;

import com.evan.sdn.entity.DomainNameMapping;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Component;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
@Component
public class LocalCacheDomainNameMappingDao implements DomainNameMappingDao {

    private final BiMap<String, String> biMap = HashBiMap.create();


    @Override
    public void save(DomainNameMapping domainNameMapping) {
        biMap.put(domainNameMapping.getShortDomainName(), domainNameMapping.getLongDomainName());
    }

    @Override
    public String getLongDomainName(String shortDomainName) {
        return biMap.get(shortDomainName);
    }

    @Override
    public String getShortDomainName(String longDomainName) {
        return biMap.inverse().get(longDomainName);
    }
}
