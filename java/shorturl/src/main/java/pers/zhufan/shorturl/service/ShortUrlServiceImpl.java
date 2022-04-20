package pers.zhufan.shorturl.service;

import org.springframework.stereotype.Service;
import pers.zhufan.shorturl.config.ShortUrlConfig;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.generator.UrlShorterGenerator;
import pers.zhufan.shorturl.storage.ShorterStorage;

import javax.annotation.Resource;

@Service
public class ShortUrlServiceImpl implements ShortUrlService{

    @Resource
    private UrlShorterGenerator urlShorterGenerator;

    @Resource
    private ShorterStorage shorterStorage;

    @Override
    public ShorterUrl generalShortUrl(ShortUrlConfig config, String longUrl) {
        return urlShorterGenerator.generate(config.getKey(), config.getLenth(), longUrl);
    }

    @Override
    public String recoverLongUrl(String shortUrl) {
        return shorterStorage.get(shortUrl);
    }

}
