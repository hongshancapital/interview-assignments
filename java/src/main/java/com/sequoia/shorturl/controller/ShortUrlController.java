package com.sequoia.shorturl.controller;

import com.sequoia.shorturl.util.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @auther: usr1999
 * @create: 2021/7/29
 */
@Api(value = "Short url service")
@RestController
@RequestMapping("/shortUrlApi")
public class ShortUrlController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "shortUrlPrefix")
    private String shortUrlPrefix;
    private static Map<String, String> shortUrlMap = new ConcurrentSkipListMap();
    private static Map<String, String> longUrlMap = new ConcurrentSkipListMap();

    /**
     * 查询长域名对应的短域名
     * @param longUrl: 长域名
     * @return
     */
    @ApiOperation(value = "Get a short url based on a long url", notes = "")
    @RequestMapping("/getShortUrl")
    public String getShortUrl(String longUrl) {
        try {
            if(StringUtils.isEmpty(longUrl)) {
                return "请输入有效的 URL";
            }

            if(!longUrl.startsWith("http")){
                return "URL需要以http开头";
            }

            String shortUrl;
            if(longUrlMap.containsKey(longUrl)) {
                shortUrl = longUrlMap.get(longUrl);
                logger.info("longUrl 对应的 shortUrl 已存在, longUrl: %s, shortUrl: %s", longUrl,
                        shortUrl);
                return shortUrlPrefix + shortUrl;
            }

            shortUrl = UrlUtil.genShortUrl();
            while (shortUrlMap.containsKey(shortUrl)) {
                logger.info("shortUrl 在 urlMap 已存在, 重新生成新url, shortUrl: %s", shortUrl);
                shortUrl = UrlUtil.genShortUrl();
            }
            shortUrlMap.put(shortUrl, longUrl);
            longUrlMap.put(longUrl, shortUrl);

            return shortUrlPrefix + shortUrl;
        } catch (Exception e) {
            logger.error("查询长域名对应的短域名异常, longUrl: %s, error msg: %s", longUrl, e);
            return "服务异常 [01]";
        }
    }

    /**
     * 查询短域名对应的长域名
     * @param shortUrl
     * @return
     */
    @ApiOperation(value = "Get a long url based on a short url")
    @RequestMapping("/getLongUrl")
    public String getLongUrl(String shortUrl) {
        try {
            if(StringUtils.isEmpty(shortUrl)) {
                return "请输入有效的 URL";
            }
            return shortUrlMap.get(shortUrl.replace(shortUrlPrefix, ""));
        } catch (Exception e) {
            logger.error("查询短域名对应的长域名异常, shortUrl: %s, error msg: %s", shortUrl, e);
            return "服务异常 [02]";
        }
    }

}

