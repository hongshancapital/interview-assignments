package com.jk.shorturl.dao;

import com.jk.shorturl.dao.storage.ShortCodeStorage;
import com.jk.shorturl.domain.ShortLongBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 这个是内存存储的实现。在内存中存储和管理短域名和长域名
 */

@Repository("shortDaoMemory")
public class ShortDaoImplMemory implements ShortDao {

    private static final Logger logger = LoggerFactory.getLogger(ShortDaoImplMemory.class);

    @Autowired
    ShortCodeStorage shortCodeStorage;

    @Override
    public String findShortCode(String longURL) {
        return shortCodeStorage.getShortCodeByLongURL(longURL);
    }

    @Override
    public String findLongURL(String shortCode) {
        return shortCodeStorage.getLongURLByshortCode(shortCode);
    }

    @Override
    public boolean saveShortCode(ShortLongBean shortLongBean) {
        return shortCodeStorage.saveShortCode(shortLongBean.getShortCode(), shortLongBean.getLongURL());
    }
}
