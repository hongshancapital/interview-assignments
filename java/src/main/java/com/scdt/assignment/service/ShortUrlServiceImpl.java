package com.scdt.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdt.assignment.config.Msg;
import com.scdt.assignment.controller.bo.ShortUrlBo;
import com.scdt.assignment.repository.ShortUrl;
import com.scdt.assignment.repository.ShortUrlRepository;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * @title ShortUrlServiceImpl.java
 * @description
 * @author
 * @date 2022-04-15 17:10:55
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    ShortUrlRepository mShortUrlRepository;

    @Override
    public ShortUrlBo create(String longUrl) {
        // 通过BK算法获取Hash值
        int longHash = HashUtil.bkdrHash(longUrl);
        // 通过Hash判断是否存在
        ShortUrl su = mShortUrlRepository.findByLongHash(longHash);
        // 不存在则新增一条记录
        if (ObjectUtil.isNull(su)) {
            su = new ShortUrl();
            su.setLongUrl(longUrl);
            su = mShortUrlRepository.insertOrUpdate(su);
        }
        return ShortUrlBo.builder().longurl(longUrl).shorturl(su.getShortUrl()).build();
    }

    @Override
    public ShortUrlBo query(String shortUrl) {
        // 通过BK算法获取Hash值
        int shortHash = HashUtil.bkdrHash(shortUrl);
        // 通过Hash判断是否存在
        ShortUrl su = mShortUrlRepository.findByShortHash(shortHash);
        if (ObjectUtil.isNull(su)) {
            throw Msg.NOT_EXIST;
        }
        return ShortUrlBo.builder().longurl(su.getLongUrl()).shorturl(su.getShortUrl()).build();
    }
}
