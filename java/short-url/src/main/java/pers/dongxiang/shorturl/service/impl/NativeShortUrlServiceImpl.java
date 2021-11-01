package pers.dongxiang.shorturl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pers.dongxiang.shorturl.config.HostConfig;
import pers.dongxiang.shorturl.service.ShortUrlService;
import pers.dongxiang.shorturl.util.ShortUrlUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.service.impl
 * @ClassName NativeShortUrlService
 * @Description 本地短地址服务
 * @Company lab
 * @Author dongxiang
 * @Date 10/29/2021 5:56 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
@Service
public class NativeShortUrlServiceImpl implements ShortUrlService {

    private ConcurrentHashMap<String,String> urlMap = new ConcurrentHashMap<>();

    @Autowired
    private HostConfig hostConfig;

    @Override
    public String createShortUrl(String originUrl) {

        Long number = ShortUrlUtil.getLongNumByShortUrl(originUrl);
        String shortUrl = ShortUrlUtil.getShortUrlByLongNum(number);

        String fullShortUrl = hostConfig.getHost().concat(shortUrl);
        urlMap.put(fullShortUrl,originUrl);

        return fullShortUrl;
    }

    @Override
    public String getOriginUrl(String shortUrl) {
        return urlMap.get(shortUrl);
    }
}
