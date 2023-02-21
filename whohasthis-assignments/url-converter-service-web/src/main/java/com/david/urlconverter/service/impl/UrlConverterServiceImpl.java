package com.david.urlconverter.service.impl;

import com.david.urlconverter.common.ResultStatusCode;
import com.david.urlconverter.common.UrlConverterException;
import com.david.urlconverter.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.david.urlconverter.service.dubbo.IUrlConverterStorageSOAService;
import com.david.urlconverter.service.web.IShortUrlLocalCacheService;
import com.david.urlconverter.service.web.IUrlConverterService;

@Service
@Slf4j
public class UrlConverterServiceImpl implements IUrlConverterService {

    @Autowired
    private NumericIdDispatcher numericIdDispatcher;

    @Autowired
    private IShortUrlLocalCacheService shortUrlLocalCacheService;

    @DubboReference(version = "${urlConverter.service.version}")
    private IUrlConverterStorageSOAService urlConverterStorageSOAService;

    @Override
    public String generateShortUrl(String longUrl) throws UrlConverterException{
        if(!UrlUtils.isValidLongUrl(longUrl)){
            throw new UrlConverterException(ResultStatusCode.ERROR_FORMAT_PARAM);
        }

        String shortUrl = null;
        //step 1. check storage service
        shortUrl = urlConverterStorageSOAService.queryShortUrl(longUrl);
        if (StringUtils.isBlank(shortUrl)) {
            //dispatch new shortUrl
            shortUrl = numericIdDispatcher.getNextId();
            urlConverterStorageSOAService.storeUrlMapping(shortUrl,longUrl);
            shortUrlLocalCacheService.storeShortToLongMapping(shortUrl, longUrl);
        }
        return shortUrl;
    }

    @Override
    public String retrieveLongUrl(String shortUrl) {
        if(!UrlUtils.isValidShortUrl(shortUrl)){
            throw new UrlConverterException(ResultStatusCode.ERROR_FORMAT_PARAM);
        }

        String longUrl = null;
        //step 1. check local cache
        longUrl = shortUrlLocalCacheService.queryLongUrlInCache(shortUrl);

        if(StringUtils.isBlank(longUrl)){
            log.debug("Local cache no match found for shor url:{}", shortUrl);

            //step 2. check dubbo cache service
            longUrl = urlConverterStorageSOAService.retrieveLongUrl(shortUrl);
            if(StringUtils.isNotBlank(longUrl)){
                shortUrlLocalCacheService.storeShortToLongMapping(shortUrl, longUrl);
            }
        }

        return longUrl;
    }


}
