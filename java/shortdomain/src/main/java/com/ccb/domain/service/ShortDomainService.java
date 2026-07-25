package com.ccb.domain.service;

import com.ccb.domain.generate.IDomainShorterGenerator;
import com.ccb.domain.generate.impl.ShorterStorageMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: nieyy
 * @Date: 2021/7/24 14:11
 * @Version 1.0
 * @Description:
 */
@Service
public class ShortDomainService {

    @Autowired
    ShorterStorageMemory shorterStorageMemory;
    @Autowired
    IDomainShorterGenerator shorterString;


    public String getShortDomainName(String longDomainName){
        String shorDomain = shorterStorageMemory.getShortDomain(longDomainName);
        if(StringUtils.isEmpty(shorDomain) ){
            shorDomain = shorterString.generate(8);
            shorterStorageMemory.save(shorDomain,longDomainName,60);
        }

        return shorDomain;
    }


    public String getLongDomainName(String shortDomainName){
        String longDomain = shorterStorageMemory.getLongDomain(shortDomainName);
        return longDomain;
    }








}
