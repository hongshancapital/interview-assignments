package com.example.demo.domainnameservice.service.impl;

import com.example.demo.domainnameservice.constant.ErrorCode;
import com.example.demo.domainnameservice.exception.DomainNameServiceException;
import com.example.demo.domainnameservice.service.DomainNameService;
import com.example.demo.domainnameservice.service.RedisService;
import com.example.demo.domainnameservice.util.EncodeUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * domain name storage and visit implementation.
 *
 * @author laurent
 * @date 2021-12-11 下午1:20
 */
@Service
public class DomainNameServiceImpl implements DomainNameService {

    @Value("${domain.name.expire:3600}")
    private Long expireTime;

    private final RedisService redisService;

    @Autowired
    public DomainNameServiceImpl(final RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * store long url.
     *
     * @param url raw url.
     * @return shortened url.
     */
    @Override
    public String storeUrl(final String url) {
        if (StringUtils.isBlank(url)) {
            throw new DomainNameServiceException(ErrorCode.INVALID_INPUT, "invalid input url");
        }
        String result =  EncodeUtil.encode(url.hashCode());
        if (StringUtils.isNotBlank(result)) {
            redisService.setValue(result, url, expireTime);
        }
        return result;
    }

    /**
     * visit short url.
     *
     * @param url shortened url.
     * @return raw url.
     */
    @Override
    public String visitUrl(final String url) {
        String result = redisService.getValue(url);
        if (StringUtils.isBlank(result)) {
            throw new DomainNameServiceException(ErrorCode.URL_NOT_FOUND, String.format("shortened url not found: %s", url));
        }
        return result;
    }

}
