package com.shorturl.facade;

import com.shorturl.domin.ShortUrlVo;
import com.shorturl.service.GuavaCacheService;
import com.shorturl.service.IUrlService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlFacade {

    private static Logger logger = LoggerFactory.getLogger(ShortUrlFacade.class);

    @Autowired
    IUrlService shortUrlService;

    @Autowired
    GuavaCacheService guavaCacheService;


    /**
     * 核心逻辑：先判断guavaCache是否已经超过最大值，如果超过最大值则抛出异常
     * url调用加密方法生成加密字符串作为短链接的后缀
     * 注意此处没有考虑撞码
     *
     * @param url
     * @return
     */
    public ShortUrlVo createShortUrl(String url) {
        String shortUrl = shortUrlService.createShortUrl(url);
        boolean putFlag = guavaCacheService.put(shortUrl, url);
        if (!putFlag) {
            return null;
        }
        ShortUrlVo shortUrlVo = new ShortUrlVo();
        shortUrlVo.setLongUrl(url);
        shortUrlVo.setShortUrl(shortUrl);
        return shortUrlVo;
    }

    /**
     * 读取缓存中的数据
     *
     * @param url
     * @return
     */
    public ShortUrlVo getLongUrl(String url) {
        try {
            String longUrl = guavaCacheService.get(url);
            if (StringUtils.isEmpty(longUrl)) {
                return null;
            }
            ShortUrlVo shortUrlVo = new ShortUrlVo();
            shortUrlVo.setLongUrl(longUrl);
            shortUrlVo.setShortUrl(url);
            return shortUrlVo;
        } catch (Exception e) {
            logger.error("查询长链接失败,原因->{}", e);
            return null;
        }
    }


}
