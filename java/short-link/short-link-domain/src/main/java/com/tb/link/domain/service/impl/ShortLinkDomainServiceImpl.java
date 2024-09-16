package com.tb.link.domain.service.impl;

import com.google.common.hash.Hashing;
import com.tb.link.domain.service.ShortLinkDomainService;
import com.tb.link.infrastructure.util.Base62String;
import org.springframework.stereotype.Service;

/**
 * @author andy.lhc
 * @date 2022/4/16 14:48
 */
@Service
public class ShortLinkDomainServiceImpl implements ShortLinkDomainService {


    @Override
    public String generateShorLink(String originUrl) {
        long t = Hashing.murmur3_128().hashUnencodedChars(originUrl).asLong();
        String shortCode = Base62String.generate8Length(t);
        return shortCode ;
    }

    @Override
    public String generateShortLinkWithRandom(String originUrl, int randomLength) {
        long t = Hashing.murmur3_128().hashUnencodedChars(originUrl).asLong();
        String shortCode = Base62String.generate(t,randomLength);
        return shortCode ;
    }


}
