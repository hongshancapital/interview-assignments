package com.chencaijie.domainservice.service;

import com.chencaijie.domainservice.bean.ResultObject;
import org.springframework.stereotype.Service;

@Service
public interface DomainService {

    /**
     * 短域名读取接口
     * @param shortDomain
     * @return
     */
    public ResultObject getLongDomainName(String shortDomain);

    /**
     * 短域名存储接口
     * @param domainName
     * @return
     */
    public ResultObject saveDomainName(String domainName);
}


