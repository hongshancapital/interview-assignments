package com.mjl.service;

import com.mjl.api.ShortUrlRepository;
import com.mjl.api.ShortUrlService;
import com.mjl.constants.UrlConstants;
import com.mjl.exception.BusinessException;
import com.mjl.utils.ShortUrlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private ShortUrlRepository shortUrlRepository;

    final Logger logger = LoggerFactory.getLogger(getClass());


    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public String getShortUrlSuffix(String longUrl) {
        logger.info("bizCode:{}<|>request:{}", "getShortUrlSuffix", longUrl);
        URI url;
        try {
            url = new URI(longUrl);
        } catch (Exception ignored) {
            throw new BusinessException("GET-SHORT-URL-INVALID-FORMAT-ERROR");
        }

        if (StringUtils.isNotEmpty(url.getHost()) && (StringUtils.equals(url.getScheme(), "http") || StringUtils.equals(url.getScheme(), "https"))) {
            return shortUrlRepository.getShortUrlSuffixFromLongUrl(longUrl);
        } else {
            throw new BusinessException("GET-SHORT-URL-INVALID-FORMAT-ERROR");
        }
    }

    @Override
    public String getLongUrlBySuffix(String shortUrlSuffix) {
        logger.info("bizCode:{}<|>request:{}", "getLongUrlBySuffix", shortUrlSuffix);
        return shortUrlRepository.getLongUrlFromShortUrlSuffix(shortUrlSuffix);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        logger.info("bizCode:{}<|>request:{}", "getLongUrl", shortUrl);
        if (StringUtils.isEmpty(shortUrl)) {
            throw new BusinessException("GET-LONG-URL-INVALID-FORMAT-ERROR");
        }
        if (!shortUrl.startsWith(UrlConstants.SHORT_URL_PREFIX)) {
            throw new BusinessException("GET-LONG-URL-INVALID-PREFIX-ERROR");
        }
        return getLongUrlBySuffix(shortUrl.substring(UrlConstants.SHORT_URL_PREFIX.length()));
    }

    @Override
    public String generateShortUrl(String longUrl) {
        logger.info("bizCode:{}<|>request:{}", "generateShortUrl", longUrl);
        String shortUrlSuffix = getShortUrlSuffix(longUrl);
        if(shortUrlSuffix != null) {
            return UrlConstants.SHORT_URL_PREFIX + shortUrlSuffix;
        }
        shortUrlSuffix = ShortUrlUtil.genShortUrlSuffix(longUrl, "");
        int repeat = 3;
        while (repeat > 0 && checkHashCollision(shortUrlSuffix)) {
            shortUrlSuffix = ShortUrlUtil.genShortUrlSuffix(longUrl, UUID.randomUUID().toString());
            repeat --;
        }
        if (checkHashCollision(shortUrlSuffix)) {
            throw new BusinessException("GENERATE-SHORT-URL-HASH-COLLISION-ERROR");
        }

        shortUrlRepository.saveUrlMap(longUrl, shortUrlSuffix);

        return UrlConstants.SHORT_URL_PREFIX + shortUrlSuffix;
    }

    private boolean checkHashCollision(String shortUrlSuffix) {
        return StringUtils.isNotEmpty(shortUrlRepository.getLongUrlFromShortUrlSuffix(shortUrlSuffix));
    }



}
