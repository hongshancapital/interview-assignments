package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.doamin.UrlStorage;
import com.scdt.yulinfu.manager.UrlStorageManager;
import com.scdt.yulinfu.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Service
public class DataStoreServiceImpl implements DataStoreService {

    @Autowired
    private UrlStorageManager manager;

    /**
     * 保存数据
     *
     * @param shortLink
     * @param longLink
     */
    @Override
    public void saveData(String shortLink, String longLink) {
        manager.save(UrlStorage.builder().shortUrl(shortLink).longUrl(longLink).build());
    }

    /**
     * 获取长链接
     *
     * @param shortLink
     * @return
     */
    @Override
    public String getLongLink(String shortLink) {
        return manager.getLongLink(shortLink);
    }
}
