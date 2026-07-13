package com.liupf.tiny.url.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.liupf.tiny.url.domain.TinyURL;
import com.liupf.tiny.url.generator.TinyURLGenerator;
import com.liupf.tiny.url.repository.ITinyURLRepository;
import com.liupf.tiny.url.service.ITinyURLService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TinyURLServiceImpl implements ITinyURLService {

    @Resource
    private ITinyURLRepository tinyURLRepository;

    @Resource
    private TinyURLGenerator tinyURLGenerator;

    @Override
    public String saveLongUrl(String longUrl) {
        TinyURL oldTinyURL = tinyURLRepository.findByLongUrl(longUrl);
        if (oldTinyURL != null) {
            return tinyURLGenerator.genShortUrl(oldTinyURL);
        }
        TinyURL tinyURL = tinyURLGenerator.build(longUrl);
        log.info("save tiny-url:{}", tinyURL);
        tinyURLRepository.saveTinyUrl(tinyURL);
        return tinyURLGenerator.genShortUrl(tinyURL);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String code = tinyURLGenerator.parseCode(shortUrl);
        return getLongUrlByCode(code);
    }

    @Override
    public String getLongUrlByCode(String code) {
        TinyURL tinyURL = tinyURLRepository.findByCode(code);
        log.info("find tiny url, code:{}, tiny-url:{}", code, tinyURL);
        return tinyURL == null ? StringUtils.EMPTY : tinyURL.getLongUrl();
    }

}
