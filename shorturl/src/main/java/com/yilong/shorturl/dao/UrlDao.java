package com.yilong.shorturl.dao;

import com.yilong.shorturl.storage.UrlStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UrlDao {

    @Autowired
    private UrlStorage urlStorage;

    public String saveUrl(String url) {
        return urlStorage.save(url);
    }

    public String getUrlByShortCode(String shortCode) {
        return urlStorage.get(shortCode);
    }
}
