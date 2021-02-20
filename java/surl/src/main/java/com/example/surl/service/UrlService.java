package com.example.surl.service;

import com.example.surl.entity.UrlDO;
import com.example.surl.mapper.UrlMapper;
import com.example.surl.utils.RedisUtil;
import com.example.surl.utils.SequenceUtil;
import com.example.surl.utils.UrlUtil;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author 杨欢
 */
@Service
public class UrlService {

    private final UrlMapper mapper;
    private final RedisUtil redisUtil;


    public UrlService(UrlMapper mapper, RedisUtil redisUtil) {
        this.mapper = mapper;
        this.redisUtil = redisUtil;
    }

    public UrlDO createSUrl(String lurl) {
        long id = SequenceUtil.nextId();
        UrlDO urlDO = new UrlDO().setCreatedAt(new Date()).setId(id).setSurl(UrlUtil.convertToStr(id)).setLurl(lurl);
        this.mapper.insertUrl(urlDO);

        setCache(urlDO);
        return urlDO;
    }


    public UrlDO getUrl(String s) {
        UrlDO urlDO = this.mapper.getUrlByUri(s);
        setCache(urlDO);
        return urlDO;
    }

    private void setCache(UrlDO urlDO) {
        redisUtil.set(RedisUtil.preL + urlDO.getLurl(), urlDO.getSurl());
        redisUtil.set(RedisUtil.preS + urlDO.getSurl(), urlDO.getLurl());
    }
}
