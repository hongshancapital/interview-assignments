package com.shorts.url.service;

import com.shorts.url.service.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短链接 service 实现
 * </p>
 *
 * @author WangYue
 * @date 2022/3/21 18:13
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private Generator generator;

    @Override
    public String getShortUrl(String longUrl) {
        return generator.generate(longUrl);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return generator.get(shortUrl);
    }
}
