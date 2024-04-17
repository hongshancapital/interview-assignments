package com.hongshan.work.service.imp;

import com.google.common.hash.BloomFilter;
import com.hongshan.work.service.UrlService;
import com.hongshan.work.mapper.HashMapUrlMap;
import com.hongshan.work.util.ConversionUtils;
import com.hongshan.work.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final SequenceGenerator sequenceGenerator;

    @Resource
    private BloomFilter<String> FILTER;

    @Autowired
    private HashMapUrlMap urlMap;

    @Override
    public String getShortUrl(String longUrl) {
        String shortUrl = getAvailableCompressCode();
        if(StringUtils.hasLength(shortUrl)) {
            urlMap.put(shortUrl, longUrl);
            FILTER.put(shortUrl);
        }
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String longUrl = null;
        if (FILTER.mightContain(shortUrl)) {
            longUrl = urlMap.get(shortUrl);
        }
        return longUrl;
    }

    private String getAvailableCompressCode() {
        String code = "";
        long sequence = sequenceGenerator.generate();
        code = ConversionUtils.X.encode62(sequence);
        return code.length() > 0 ? code.substring(code.length() - 8) : code;
    }

}

