package com.zh.shortUrl.service.impl;

import com.zh.shortUrl.common.BaseResponse;
import com.zh.shortUrl.service.ShortUrlService;
import com.zh.shortUrl.util.ShortUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author hang.zhang
 * @Date 2022/1/18 16:32
 * @Version 1.0
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    @Autowired
    ShortUrlUtils shortUrlUtils;

    @Override
    public BaseResponse getShortUrl(String longUrl) {
        return new BaseResponse(shortUrlUtils.getShortUrl(longUrl));
    }

    @Override
    public BaseResponse getLongUrl(String shortUrl) {
        String longUrl = shortUrlUtils.getLongUrl(shortUrl);
        if(null==longUrl){
            return new BaseResponse(-1,"无匹配的链接信息",null);
        }else{
            return new BaseResponse(longUrl);
        }
    }
}
