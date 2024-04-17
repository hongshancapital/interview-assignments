package com.wg.service.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.wg.common.ResultCodeEnum;
import com.wg.exception.ServiceException;
import com.wg.service.DomainService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DomainServiceImpl implements DomainService {

    private static final String SHORT_URL_DNS = "https://test:9999/";

    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    @Override
    public String getShortUrl(String realUrl) {
        HashCode hashCode = Hashing.murmur3_32().hashString(realUrl, StandardCharsets.UTF_8);
        String code = hashCode.toString();
        String shortUrl = SHORT_URL_DNS + code;
        map.put(shortUrl, realUrl);
        return shortUrl;
    }

    @Override
    public String getRealUrl(String shortCode) {
        boolean b = map.containsKey(shortCode);
        // 存在，直接返回短连接
        if(!b) {
            throw new ServiceException(ResultCodeEnum.LINK_INVALID.getCode(), ResultCodeEnum.LINK_INVALID.getMessage());
        }
        return map.get(shortCode);
    }

}
