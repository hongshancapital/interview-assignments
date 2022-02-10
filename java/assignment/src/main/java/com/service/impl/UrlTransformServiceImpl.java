package com.service.impl;

import com.constant.CommonConstants;
import com.exception.SystemException;
import com.service.IMatchStorageService;
import com.service.IUrlTransformService;
import com.utils.GuavaUtil;
import com.utils.ShortUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 长短链接转换实现类
 */
@Service
public class UrlTransformServiceImpl implements IUrlTransformService {
    private Logger logger = LoggerFactory.getLogger(UrlTransformServiceImpl.class);
    @Autowired
    private IMatchStorageService iMatchStorageService;
    /**
     * 根据长链接获取短链接
     * @param longUrl
     * @return
     */
    @Override
    public String getShortUrl(String longUrl) {
        logger.info("get Short Url " + longUrl);
        //从缓存中根据长链接获取短链接
        String shortUrl = iMatchStorageService.getShortUrlCache(longUrl);
        if(shortUrl != null){
            return shortUrl;
        }else{
            int shortUrlCacheSize = iMatchStorageService.getShortUrlCacheSize();
            if (shortUrlCacheSize >= CommonConstants.MAX_CACHE_SIZE) {
                logger.warn("max cache size reached, " + shortUrlCacheSize);
                throw new SystemException(CommonConstants.CODE_SERVICE_UNAVAILABLE, "生产短链接服务内存溢出");
            }
            String shortKey = ShortUrlUtils.getShortText((shortUrlCacheSize));
            if(shortKey.length()>8){
                throw new SystemException(CommonConstants.CODE_SERVICE_UNAVAILABLE,"生产短链接服务异常");
            }
            //添加长-短链接缓存
            iMatchStorageService.setShortLongUrlCache(longUrl,CommonConstants.BASE_URL + "/" + shortKey);

            return CommonConstants.BASE_URL + "/" + shortKey;
        }
    }

    /**
     * 根据短链接获取长链接
     * @param shortUrl
     * @return
     */
    @Override
    public String getLongUrl(String shortUrl) {
        logger.info("get Long Url " + shortUrl);
        //从缓存中根据长链接获取短链接
        String longUrl = iMatchStorageService.getLongUrlCache(shortUrl);
        if(longUrl != null){
            return longUrl;
        }else{
            logger.info("获取长链接失败");
            throw new SystemException(CommonConstants.CODE_LONG_URL_NOT_FOUND,"请检查短链接是否合法");
        }
    }
}
