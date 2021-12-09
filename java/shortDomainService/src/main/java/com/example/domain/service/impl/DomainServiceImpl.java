package com.example.domain.service.impl;

import com.example.domain.service.DomainService;
import com.example.domain.service.ShortDomainGenerateRuleStrategy;
import com.example.domain.service.entity.Domain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @title: DomainServiceImpl
 * @Author DH
 * @Date: 2021/12/6 15:33
 */
@Slf4j
@Service
public class DomainServiceImpl implements DomainService {


    @Resource
    private ShortDomainGenerateRuleStrategy shortDomainGenerateRuleStrategy;

    @Resource
    private Map<String, Domain> domainMap;

    @Value("${shortDomain.length}")
    public String length;



    @Override
    public String getDomain8ShortDomain(String shortDomain) {

        if(StringUtils.isBlank(shortDomain)) {
            log.info("shortDomain is blank");
            return "";
        }
        if(!domainMap.containsKey(shortDomain)) {
            log.info("{} do not exist",shortDomain);
        }
        Domain domainEntity = domainMap.get(shortDomain);
        return domainEntity.getDomain();
    }

    @Override
    public String addDomainInfo(String domain) {
        if(StringUtils.isBlank(domain)) {
            log.info("domain is blank");
            return "";
        }
        byte[] encode = Base64Utils.encode(domain.getBytes());
        String shortDomain = new String(encode);
        if(shortDomain.length()>Integer.valueOf(length)) {
            shortDomain = StringUtils.substring(shortDomain,0,Integer.valueOf(length));
        }
        log.info("domain:{},shortDomain:{}",domain,shortDomain);
        Domain domainEntity = new Domain();
        domainEntity.setDomain(domain);
        domainEntity.setShortDomain(shortDomain);
        domainEntity.setId((Long) shortDomainGenerateRuleStrategy.actionRule());
        domainMap.put(shortDomain,domainEntity);
        return shortDomain;
    }
}
