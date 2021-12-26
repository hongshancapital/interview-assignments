package com.sequoia.domain.service.impl;

import com.sequoia.domain.exception.UrlNotExistException;
import com.sequoia.domain.service.IShortDomainService;
import com.sequoia.domain.service.IUrlCacheService;
import com.sequoia.domain.service.IdGenerator;
import com.sequoia.domain.util.NumericUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ShortDomainServiceImpl implements IShortDomainService {
    @Value("${app.domain.url_prefix}")
    private String urlPrefix;

    private final IdGenerator idGenerator;

    private final IUrlCacheService urlCacheService;

    @Autowired
    public ShortDomainServiceImpl(IdGenerator idGenerator, IUrlCacheService urlCacheService) {
        this.idGenerator = idGenerator;
        this.urlCacheService = urlCacheService;
    }

    @Override
    public String toShortUrl(String url) {
        long id = idGenerator.nextId();
        String shortUrl = NumericUtils.toBinary62(id);
        urlCacheService.put(id, url);
        return urlPrefix + "/" + shortUrl;
    }

    @Override
    public String restoreToOriginUrl(String url) {
        String decodedUrl = new String(Base64.getDecoder().decode(url), StandardCharsets.UTF_8);
        int index = decodedUrl.lastIndexOf('/');
        if (index < 0) {
            throw new IllegalArgumentException("url is invalid");
        }
        String urlSuffix = decodedUrl.substring(index);
        String longEncodedUrl = urlCacheService.get(NumericUtils.toDecimal(urlSuffix));
        if (StringUtils.isEmpty(longEncodedUrl)) {
            throw new UrlNotExistException("Your URL does not exist or has expired");
        }
        return new String(Base64.getDecoder().decode(longEncodedUrl), StandardCharsets.UTF_8);
    }
}
