package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.service.DataService;
import com.scdt.yulinfu.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataStoreService dataStoreService;

    /**
     * 获取短链接
     *
     * @param longLink
     * @return
     */
    @Override
    public String getShortLink(String longLink) {
        return dataStoreService.getShortLink(longLink);
    }

    /**
     * 获取长链接
     *
     * @param shortLink
     * @return
     */
    @Override
    public String getLongLink(String shortLink) {
        return dataStoreService.getLongLink(shortLink);
    }
}
