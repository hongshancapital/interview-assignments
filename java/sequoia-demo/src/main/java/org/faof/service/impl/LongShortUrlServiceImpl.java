package org.faof.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.faof.ApplicationContext;
import org.faof.exceptions.BizException;
import org.faof.exceptions.ExceptionEnum;
import org.faof.property.ApplicationProperty;
import org.faof.service.ILongShortUrlService;
import org.faof.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LongShortUrlServiceImpl  implements ILongShortUrlService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    private AtomicLong urlCount;
    private List<String[]> shortUrlBuffer;
    private int bufferArraySize = Integer.MAX_VALUE;
    private Cache<String, Long> urlCache;

    @Autowired
    ApplicationProperty applicationProperty;

    @PostConstruct
    public void init() {
        initService();
    }

    @Override
    public String getLong2ShortUrl(String longUrl) {
        checkLongUrl(longUrl);
        long hexShortUrl = urlCache.get(longUrl, x -> {
            long hex = urlCount.incrementAndGet();
            if (hex >= applicationProperty.getMaxCapacity()) {
                throw new BizException(ExceptionEnum.MEMORY_OVERFLOW);
            }
            int listIndex = (int) (hex / bufferArraySize);
            int arrayIndex = (int) (hex % bufferArraySize);
            if (shortUrlBuffer.size() <= listIndex) {
                shortUrlBuffer.add(new String[bufferArraySize]);
            }
            shortUrlBuffer.get(listIndex)[arrayIndex] = longUrl;
            return hex;
        });
        return applicationProperty.getShortUrlPrefix() + UrlUtils.hexTo62(hexShortUrl);
    }

    @Override
    public String getShort2LongUrl(String shortUrl) {
        checkShortUrl(shortUrl);
        String _62 = shortUrl.replace(applicationProperty.getShortUrlPrefix(), "");
        long hex = UrlUtils._62ToHex(_62);
        long nowCount = urlCount.get();
        if (hex > nowCount) {
            return "";
        }
        int listIndex = (int) (hex / bufferArraySize);
        int arrayIndex = (int) (hex % bufferArraySize);
        return shortUrlBuffer.get(listIndex)[arrayIndex];
    }

    @Override
    public void initService() {
        urlCount = new AtomicLong(-1);
        shortUrlBuffer = new ArrayList<>();
        bufferArraySize = applicationProperty.getArraySize();
        urlCache = Caffeine
                .newBuilder()
                .initialCapacity(applicationProperty.getCacheInitCapacity())
                .maximumSize(applicationProperty.getCacheMaxSize())
                .build();
    }

    private void checkLongUrl(String longUrl) {
        if (longUrl == null) {
            throw new BizException(ExceptionEnum.REQUEST_BODY_INVALID);
        }
        if (!UrlUtils.isUrl(longUrl)) {
            throw new BizException(ExceptionEnum.LONG_URL_INVALID);
        }
    }

    private void checkShortUrl(String shortUrl) {
        if (shortUrl == null) {
            throw new BizException(ExceptionEnum.REQUEST_BODY_INVALID);
        }
        if (!UrlUtils.isUrl(shortUrl)) {
            throw new BizException(ExceptionEnum.SHORT_URL_INVALID);
        }
        if (!shortUrl.startsWith(applicationProperty.getShortUrlPrefix())) {
            throw new BizException(ExceptionEnum.SHORT_URL_INVALID);
        }
        String _62 = shortUrl.replace(applicationProperty.getShortUrlPrefix(), "");
        if (_62.length() != 8) {
            throw new BizException(ExceptionEnum.SHORT_URL_INVALID);
        }
        char[] chars = _62.toCharArray();
        for (int i = 0; i < 8; i++) {
            if (!((chars[i] >= '0' && chars[i] <= '9') || (chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z'))) {
                throw new BizException(ExceptionEnum.SHORT_URL_INVALID);
            }
        }
    }
}
