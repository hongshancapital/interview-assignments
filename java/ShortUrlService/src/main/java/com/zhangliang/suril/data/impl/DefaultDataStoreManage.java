package com.zhangliang.suril.data.impl;

import com.zhangliang.suril.data.DataStoreManage;
import com.zhangliang.suril.util.AssertUtils;
import com.zhangliang.suril.util.MemoryDataStore;
import javax.annotation.Resource;
import javax.annotation.Resources;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 数据存储管理
 *
 * @author zhang
 * @date 2021/12/01
 */
@Repository
public class DefaultDataStoreManage implements DataStoreManage {

    @Resource
    private MemoryDataStore memoryDataStore;

    /**
     * 存储网站信息
     *
     * @param originalUrl {@see String} 原始url
     * @param shortUrl {@see String} 处理后的短url
     */
    @Override
    public void store(String shortUrl, String originalUrl) {
        AssertUtils.isUrl(shortUrl);
        AssertUtils.isUrl(originalUrl);
        memoryDataStore.set(shortUrl, originalUrl);
    }

    /**
     * 获取原始域名
     *
     * @param shortUrl {@see String} 短网址
     * @return 返回原始Url {@see String} 信息
     */
    @Override
    public String fetch(String shortUrl) {
        AssertUtils.isUrl(shortUrl);
        return memoryDataStore.get(shortUrl);
    }

    /**
     * 是否已经存在当前短地址
     *
     * @param shortUrl 短网址
     * @return boolean
     */
    @Override
    public boolean exists(String shortUrl) {
        return memoryDataStore.exists(shortUrl);
    }
}
