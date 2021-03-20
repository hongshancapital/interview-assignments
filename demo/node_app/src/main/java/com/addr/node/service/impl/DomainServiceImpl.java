package com.addr.node.service.impl;

import com.addr.node.mapper.DomainMapper;
import com.addr.node.service.DomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DomainServiceImpl implements DomainService {

    @Resource
    private DomainMapper domainMapper;

    public int insertDomain(Map<String, String> map) {
        return domainMapper.insertDomain(map);
    }

    public String queryDomain(String shortDoamin) {
        return domainMapper.queryDomain(shortDoamin);
    }
}
