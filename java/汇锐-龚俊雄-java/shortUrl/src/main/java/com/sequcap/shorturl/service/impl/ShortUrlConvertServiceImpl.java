package com.sequcap.shorturl.service.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.sequcap.shorturl.service.ShortUrlConvertService;
import com.sequcap.shorturl.service.UrlManagementService;
import com.sequcap.shorturl.web.exception.BusinessException;
import com.sequcap.shorturl.web.model.UrlModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class ShortUrlConvertServiceImpl implements ShortUrlConvertService {

    Logger log = LoggerFactory.getLogger(ShortUrlConvertServiceImpl.class);

    private static final String CHAR_REPEATED = "[REPEATED]";
    
    private final UrlManagementService urlManagementService;

    public ShortUrlConvertServiceImpl(UrlManagementService urlManagementService) {
        this.urlManagementService = urlManagementService;
    }

    @Override
    public UrlModel long2Short(String longUrl) {
        log.info("***** Convert longUrl to shortUrl, longUrl={}", longUrl);
        HashFunction function = Hashing.murmur3_32();
        HashCode murmurCode = function.hashString(longUrl, Charset.forName("utf-8"));

        UrlModel urlModel = urlManagementService.getUrlModel(murmurCode.toString());
        if (urlModel != null) {
            // 如果缓存里有值，并且longUrl不相等，说明hascode生成重复了，需要再次生成（小概率事件）
            if ( ! StringUtils.equals(urlModel.getLongUrl(), longUrl)) {
                // 增加特定字符后，再次生成hashcode
                return long2Short(longUrl + CHAR_REPEATED);
            }

            // 如果相等，直接返回
            return urlModel;
        }

        return urlManagementService.buildUrlModel(murmurCode.toString(), longUrl);
    }

    @Override
    public UrlModel getLongUrlByShort(String shortUrl) {
        log.info("***** get longUrl by shortUrl, shortUrl={}", shortUrl);
        int index = shortUrl.lastIndexOf("/");
        if (index <= 0) {
            log.error("***** get longUrl with error format shortUrl, shortUrl={}", shortUrl);
            throw new BusinessException("The shortUrl format is not correct, shortUrl=" + shortUrl);
        }

        String murmurCode = shortUrl.substring(index+1);
        UrlModel urlModel = urlManagementService.getUrlModel(murmurCode);
        if (urlModel != null) { // 整体替换CHAR_REPEATED
            urlModel.setLongUrl(urlModel.getLongUrl().replaceAll(CHAR_REPEATED, ""));
            return urlModel;
        }

        return null;
    }

}
