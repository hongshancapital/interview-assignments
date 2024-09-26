package com.alibaba.service.impl;

import com.alibaba.service.IDomainService;
import com.alibaba.utils.ShortUrlUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozx
 * @desc
 * @date 2022年04月19日 15:57
 */
@Service
public class DomainServiceImpl implements IDomainService {

    private static final Map<String,String> dataMap = new HashMap<>();

    @Override
    public String getShortDomain(String url) {
        String shortUrl = ShortUrlUtils.shortUrl(url);
        dataMap.put(shortUrl, url);
        return shortUrl;
    }

    @Override
    public String getLongDomain(String url) {
        String longUrl = dataMap.get(url);
        return longUrl;
    }
}
