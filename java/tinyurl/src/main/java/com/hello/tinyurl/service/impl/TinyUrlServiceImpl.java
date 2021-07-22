package com.hello.tinyurl.service.impl;

import com.hello.tinyurl.dao.TinyUrlCacheDao;
import com.hello.tinyurl.service.TinyUtlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Shuai
 * @date: 2021-7-5 21:55
 * @description:
 */
@Service
public class TinyUrlServiceImpl implements TinyUtlService {

    @Resource(name = "tinyUrlCacheDao")
    private TinyUrlCacheDao tinyUrlDao;

    @Override
    public String saveOriginalUrl(String originalUrl) throws Exception {
        return tinyUrlDao.saveOriginalUrl(originalUrl);
    }

    @Override
    public String getOriginalUrl(String tinyUrl) throws Exception {
        return tinyUrlDao.getOriginalUrl(tinyUrl);
    }
}
