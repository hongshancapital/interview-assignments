package com.sxg.shortUrl.service;

import org.springframework.stereotype.Service;
import com.sxg.shortUrl.mapper.UrlMapper;
import com.sxg.shortUrl.model.UrlModel;
import com.sxg.shortUrl.utils.RedisUtil;
import com.sxg.shortUrl.utils.SnowflakeIdWorker;
import com.sxg.shortUrl.utils.UrlUtil;

import java.util.Date;


/**
 * 
 * @author sxg
 *
 */
@Service
public class UrlService {

    private final UrlMapper mapper;
    private final RedisUtil redisUtil;


    public UrlService(UrlMapper mapper, RedisUtil redisUtil) {
        this.mapper = mapper;
        this.redisUtil = redisUtil;
    }

    public UrlModel createShortUrl(String longUrl) {
    	
    	SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    	long id =  idWorker.nextId();
        UrlModel urlModel = new UrlModel();
        urlModel.setCreateDate(new Date());
        urlModel.setId(id);
        urlModel.setLongUrl(longUrl);
        urlModel.setShortUrl(UrlUtil.convertToStr(id));
        this.mapper.insertUrl(urlModel);
        setCache(urlModel);
        return urlModel;
    }


    public UrlModel getUrl(String s) {
        UrlModel urlModel = this.mapper.getUrlByUri(s);
        setCache(urlModel);
        return urlModel;
    }

    private void setCache(UrlModel urlModel) {
        redisUtil.set(RedisUtil.urlLong + urlModel.getLongUrl(), urlModel.getShortUrl());
        redisUtil.set(RedisUtil.urlShort + urlModel.getShortUrl(), urlModel.getLongUrl());
    }
}
