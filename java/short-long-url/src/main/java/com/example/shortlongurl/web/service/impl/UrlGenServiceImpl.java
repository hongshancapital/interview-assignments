package com.example.shortlongurl.web.service.impl;

import com.example.shortlongurl.web.service.BaseUrlGenService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;


/**
 * Url长短链接转换实现类-MD5取长链接摘要作为缓存key
 */
@Service
public class UrlGenServiceImpl extends BaseUrlGenService {
    // - 生成随机盐
    private String salt = UUID.randomUUID().toString().toUpperCase();

    @Override
    public String genLongKey(String longUrl) {

        longUrl = salt+longUrl + salt;
        return DigestUtils.md5DigestAsHex(longUrl.getBytes());
    }
}
