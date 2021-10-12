package com.meihua.shorturl.cmdb.impl;

import com.meihua.shorturl.cmdb.ShortUrlGenerator;
import com.meihua.shorturl.util.IDGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ConcurrentHashMap;
/**
 *  <p>
 *   MD5模式
 *  </p>
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public class Md5GeneratorShortUrlHandler implements ShortUrlGenerator {

    private static final Logger logger = LoggerFactory.getLogger(Md5GeneratorShortUrlHandler.class);

    private static ConcurrentHashMap<String,String> map;

    private static int maxCount;

    @Value("${url.max-count:512}")
    public  void setMaxCount(int maxCount) {
        Md5GeneratorShortUrlHandler.maxCount = maxCount*1024;
    }

    @Override
    public void destroy() {
        logger.warn("MD5GeneratorShortUrlHandler destroy ! ");
    }

    @Override
    public void init() {
        map=new ConcurrentHashMap<>(maxCount);
        logger.info("MD5GeneratorShortUrlHandler init done ! maxCount: {} ",maxCount);
    }

    @Override
    public String getValue(String key) {
        return map.get(key);
    }

    @Override
    public String put(String url) {
        String shortUrl = IDGeneratorUtils.shortUrl(url);
        map.put(shortUrl,url);
        return shortUrl;
    }
}
