package com.scdt.url.tiny_url.model;

import com.scdt.url.common.configuration.AppConfiguration;
import com.scdt.url.common.ddd.DomainService;
import com.scdt.url.common.util.ShortStringGenerator;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlIdGenerator implements DomainService {

    private final AppConfiguration appConfiguration;

    public TinyUrlIdGenerator(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    public TinyUrlId generate(String originalUrl) {
        return TinyUrlId.tinyUrlId(ShortStringGenerator.generate(originalUrl, appConfiguration.getTinyUrlLength()));
    }
}
