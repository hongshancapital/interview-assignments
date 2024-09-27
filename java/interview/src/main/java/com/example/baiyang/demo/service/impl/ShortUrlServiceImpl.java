package com.example.baiyang.demo.service.impl;

import com.example.baiyang.demo.domain.UrlEntity;
import com.example.baiyang.demo.service.ShortUrlService;
import com.example.baiyang.demo.utils.ShortUrlGenerateUtil;
import com.example.baiyang.demo.utils.ShortUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description:短域名服务接口实现类
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlUtil shortUrlUtil;

    @Override
    public String getShortUrl(String longUrl, String digest) {

        //从内存中读取对应的短域名
        String res = shortUrlUtil.getShortUrl(longUrl);
        if (!StringUtils.isBlank(res)) {
            return res;
        }

        //如果没有找到，则生成一个短域名，并且存储映射关系
        String shortUrl = ShortUrlGenerateUtil.getRandomShortUrl(longUrl, digest);

        if (!StringUtils.isBlank(shortUrl)) {
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setLongUrl(longUrl);
            urlEntity.setShortUrl(shortUrl);

            shortUrlUtil.save(urlEntity);
        }

        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return shortUrlUtil.getLongUrl(shortUrl);
    }
}
