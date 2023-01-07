package com.skyscreen.urldemo.service.impl;

import com.skyscreen.urldemo.utils.PatternUtils;
import com.skyscreen.urldemo.cache.MapCache;
import com.skyscreen.urldemo.service.ConvertUrlService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ConvertUrlServiceImpl implements ConvertUrlService {

    private final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final String shortUrlPrefix = "http://c.cn/";

    private static Logger logger = LoggerFactory.getLogger(ConvertUrlServiceImpl.class);

    //需要经过测试所得极限大小值
    private final Integer MAX_MAP_SIZE = 10000000;


    /**
     * 给出长链接转化为短链接
     * @param longUrl
     * @return
     */
    @Override
    public String convertToShortUrl(String longUrl){
        String shortUrl = StringUtils.EMPTY;

        logger.info("当前Map的大小为:"+ MapCache.map.size());


        //校验还需要细化
        if (StringUtils.isBlank(longUrl) || !PatternUtils.checkIfValidUrl(longUrl)){
            return shortUrl;
        }

        if (MapCache.map.containsValue(longUrl)) {
            logger.info("get from cache" + longUrl);
            return shortUrlPrefix + MapCache.getLongKey(longUrl);
        }
        else {
            //生成短链接
            return generateShortUrl(longUrl);
        }

    }

    @Override
    public String convertToLongUrl(String shortUrl){
        String longUrl = StringUtils.EMPTY;

        //校验还需要细化
        if (StringUtils.isBlank(shortUrl) || shortUrl.indexOf(shortUrlPrefix) != 0|| !PatternUtils.checkIfValidUrl(shortUrl)){
            return "invalid url";
        }

        //获取短链接字符串
        String shortUrlStr = shortUrl.replace(shortUrlPrefix,"");
        logger.info("最终短链接字符串:"+shortUrlStr);

        return MapCache.map.get(shortUrlStr);
    }

    private String generateShortUrl(String longUrl){

        //资源有限防止溢出
        if (MapCache.map.size()==MAX_MAP_SIZE) {
            logger.info("为防止溢出只返回前缀字符串");
            return shortUrlPrefix;
        }
        String key = createKey();
        while (MapCache.map.containsKey(key)) {
            key = createKey();
        }
        MapCache.map.put(key, longUrl);
        return shortUrlPrefix + key;
    }

    private String createKey(){
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(BASE62.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }

}
