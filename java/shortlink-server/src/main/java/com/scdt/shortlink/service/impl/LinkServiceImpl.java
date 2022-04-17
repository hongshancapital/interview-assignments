package com.scdt.shortlink.service.impl;

import com.google.common.hash.BloomFilter;
import com.scdt.shortlink.config.AplicationConfig;
import com.scdt.shortlink.service.LinkService;
import com.scdt.shortlink.util.ConversionUtil;
import com.scdt.shortlink.util.sequence.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xbhong
 * @date 2022/4/17 16:15
 */
@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final SequenceGenerator sequenceGenerator;

    @Autowired
    private AplicationConfig cacheConfig;

    @Resource
    private BloomFilter<String> FILTER;

    @Value("${shortlink.domain}")
    private String domain;

    @Override
    public String createShortLink(String longLink) {
        String alias = ConversionUtil.X.encode62(sequenceGenerator.generate());
        cacheConfig.caffeineCache().put(alias, longLink);
        FILTER.put(alias);
        StringBuilder sb = new StringBuilder();
        sb.append(domain);
        sb.append(alias);
        return sb.toString();
    }

    @Override
    public String getLongLink(String shortLink) {
        String longLink = null;
        //去掉域名domain
        shortLink = shortLink.substring(shortLink.lastIndexOf("/") + 1);
        if (FILTER.mightContain(shortLink)) {
            longLink = (String) cacheConfig.caffeineCache().getIfPresent(shortLink);
        }
        return longLink;
    }
}
