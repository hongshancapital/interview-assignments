package com.youyuzuo.shortdn.service;

import com.youyuzuo.shortdn.dto.DomainNameDto;
import com.youyuzuo.shortdn.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainNameServiceImpl implements DomainNameService{

    @Autowired
    private Storage storage;
    //短域名存储接口：接受长域名信息，返回短域名信息

    public DomainNameDto save(String longDn){
        if(longDn==null){
            return null;
        }
        DomainNameDto dto = new DomainNameDto(null,longDn);
        return storage.save(dto);
    }

    //短域名读取接口：接受短域名信息，返回长域名信息。
    public DomainNameDto queryLongDn(String shortDn){
        if(shortDn==null){
            return null;
        }
        return storage.search(shortDn);
    }
}
