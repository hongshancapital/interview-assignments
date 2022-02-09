package com.evan.sdn.service.impl;

import com.evan.sdn.dto.DomainNameMappingDao;
import com.evan.sdn.entity.DomainNameMapping;
import com.evan.sdn.service.DomainNameMappingService;
import com.evan.sdn.util.SnowflakeIdGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
@Service
@RequiredArgsConstructor
public class DomainNameMappingServiceImpl implements DomainNameMappingService {

    private final DomainNameMappingDao domainNameMappingDao;

    @Override
    public String save(String longDomainName) {
        String shortDomainName = domainNameMappingDao.getShortDomainName(longDomainName);
        if (shortDomainName == null) {
            shortDomainName = SnowflakeIdGeneratorUtil.nextId();
            domainNameMappingDao.save(DomainNameMapping.builder().shortDomainName(shortDomainName).longDomainName(longDomainName).build());
        }
        return shortDomainName;
    }

    @Override
    public String query(String shortDomainName) {
        return domainNameMappingDao.getLongDomainName(shortDomainName);
    }
}
