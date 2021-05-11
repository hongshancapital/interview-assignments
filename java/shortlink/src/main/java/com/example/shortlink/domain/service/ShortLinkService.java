package com.example.shortlink.domain.service;

import com.example.shortlink.domain.entity.ShortLink;
import com.example.shortlink.domain.repository.ShortLinkRepository;
import com.example.shortlink.infrastructure.codec.CodecStrategy;
import com.example.shortlink.infrastructure.common.IdGenerator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShortLinkService {
    private static Logger logger = LoggerFactory.getLogger(ShortLinkService.class);
    @Autowired
    private ShortLinkRepository shortlinkRepository;
    @Autowired
    private CodecStrategy codecStrategy;

    public ShortLink genShortLink(String longLink) {
        if (Strings.isEmpty(longLink)) {
            return ShortLink.builder().build();
        }
        //generate unique resource id
        long uniqueId = IdGenerator.getUniqueIdWithBloomFilter(longLink);
        logger.info("the uniqueId is {}", uniqueId);
        //encode unique id
        String encodeLinkKey = codecStrategy.encode(uniqueId);
        //make return object
        ShortLink shortLink = ShortLink.builder().build();
        shortLink.setId(uniqueId);
        shortLink.setShortLinkKey(encodeLinkKey);
        shortLink.setLongLink(longLink);
        //save to storage
        shortlinkRepository.saveShortLink(shortLink);
        return shortLink;
    }

    public String getSourceLink(String shortLinkKey) {
        return shortlinkRepository.getSourceLinkByShortLinkKey(shortLinkKey);
    }


}
