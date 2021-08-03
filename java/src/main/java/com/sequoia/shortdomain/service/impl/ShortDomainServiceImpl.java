package com.sequoia.shortdomain.service.impl;

import com.sequoia.shortdomain.common.ShortDomainCache;
import com.sequoia.shortdomain.common.ShortDomainConst;
import com.sequoia.shortdomain.common.ShortDomainCreate;
import com.sequoia.shortdomain.service.IShortDomainService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class ShortDomainServiceImpl implements IShortDomainService {



    /**
     * @param longDomain long domain
     * @return short domain
     */
    @Override
    public String getShortDomainByLongDomain(String longDomain) {
        if(StringUtils.isBlank(longDomain)){
            return Strings.EMPTY;
        }

        String shortDomain=ShortDomainCache.getCache(longDomain);
        if(StringUtils.isNotBlank(shortDomain)){
            return shortDomain;
        }
        shortDomain= ShortDomainCreate.shortenCodeUrl(longDomain, ShortDomainConst.SHORT_DOMAIN_LENGTH);
        if(StringUtils.isBlank(shortDomain)){
            return Strings.EMPTY;
        }

        ShortDomainCache.addCache(shortDomain,longDomain);
        ShortDomainCache.addCache(longDomain,shortDomain);

        return shortDomain;
    }

    @Override
    public String getLongDomainByShortDomain(String shortDomain) {
        if(StringUtils.isBlank(shortDomain)){
            return Strings.EMPTY;
        }

        String longDomain=ShortDomainCache.getCache(shortDomain);
        if(StringUtils.isNotBlank(shortDomain)){
            return longDomain;
        }else{
            return Strings.EMPTY;
        }
    }
}
