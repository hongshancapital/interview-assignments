package com.scdt.demo.service;

import com.scdt.demo.common.URLUtils;
import com.scdt.demo.dao.caffeine.CaffeineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortURLServiceImpl implements ShortURLService {

    private static final String DOMAIN = "https://scdt.cn/";

    private final CaffeineRepository caffeineRepository;

    @Autowired
    public ShortURLServiceImpl(CaffeineRepository caffeineRepository) {
        this.caffeineRepository = caffeineRepository;
    }

    @Override
    public String shortenUrl(String originUrl) {
        String shortUrl = URLUtils.generateShortUrl(originUrl);
        String link = DOMAIN + shortUrl;
        caffeineRepository.put(shortUrl, originUrl);
        return link;
    }

    @Override
    public Optional<String> getOriginUrl(String shortUrl) {
        return caffeineRepository.get(shortUrl, String.class);
    }
}
