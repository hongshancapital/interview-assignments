package com.chencaijie.domainservice.service;

import org.springframework.stereotype.Service;

@Service
public interface DomainService {
    public String getLongDomainName( String shortDomain);

    public String saveDomainName( String domainName);
}


