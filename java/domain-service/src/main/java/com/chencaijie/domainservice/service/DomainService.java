package com.chencaijie.domainservice.service;

import org.springframework.stereotype.Service;

@Service
public interface DomainService {

    /**
     * 短域名读取接口
     * @param shortDomain
     * @return
     */
    public String getLongDomainName( String shortDomain);

    /**
     * 短域名存储接口
     * @param domainName
     * @return
     */
    public String saveDomainName( String domainName);
}


