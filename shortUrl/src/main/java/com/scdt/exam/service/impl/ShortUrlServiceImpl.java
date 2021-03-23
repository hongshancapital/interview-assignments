package com.scdt.exam.service.impl;

import com.scdt.exam.dao.ShortUrlMapper;
import com.scdt.exam.model.ShortUrl;
import com.scdt.exam.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlMapper shortUrlMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return shortUrlMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShortUrl record) {
        return shortUrlMapper.insert(record);
    }

    @Override
    public int insertSelective(ShortUrl record) {
        return shortUrlMapper.insertSelective(record);
    }

    @Override
    public ShortUrl selectByPrimaryKey(Long id) {
        return shortUrlMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShortUrl record) {
        return shortUrlMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShortUrl record) {
        return shortUrlMapper.updateByPrimaryKey(record);
    }

    @Override
    public ShortUrl selectByCode(String tableName, String code) {
        return shortUrlMapper.selectByCode(tableName, code);
    }

    @Override
    public void insertWithTableName(ShortUrl sUrl) {
        shortUrlMapper.insertWithTableName(sUrl);
    }
}
