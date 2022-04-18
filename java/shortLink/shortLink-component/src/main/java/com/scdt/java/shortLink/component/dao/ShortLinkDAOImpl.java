package com.scdt.java.shortLink.component.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.scdt.java.shortLink.component.util.UrlCacheUtil;

@Service
public class ShortLinkDAOImpl implements ShortLinkDAO {

    @Resource
    private UrlCacheUtil urlCacheUtil;

    @Override
    public void saveLinkRelation(String key, String val) {
        urlCacheUtil.saveLinkRelation(key, val);
    }

    @Override
    public String getShortFromLong(String key) {
        return urlCacheUtil.getShortFromLong(key);
    }

    @Override
    public String getLongFromShort(String key) {
        return urlCacheUtil.getLongFromShort(key);
    }
}
