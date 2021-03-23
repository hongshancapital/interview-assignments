package com.scdt.exam.service;

import com.scdt.exam.model.ShortUrl;

public interface ShortUrlService {

    int deleteByPrimaryKey(Long id);

    int insert(ShortUrl record);

    int insertSelective(ShortUrl record);

    ShortUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShortUrl record);

    int updateByPrimaryKey(ShortUrl record);

    ShortUrl selectByCode(String tableName, String code);

    void insertWithTableName(ShortUrl sUrl);
}
