package com.scdt.shorturl.service.impl;

import cn.hutool.core.util.StrUtil;
import com.scdt.shorturl.common.ShortUrlUtil;
import com.scdt.shorturl.exception.ServerException;
import com.scdt.shorturl.service.ShortUrlService;
import com.scdt.shorturl.utils.UrlCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.md5Key}")
    private String md5Key;

    public String shorterUrl(String url) {
        String shortUrl;
        final String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
        if(UrlCache.containsUrl(md5)){//如果长域名已经请求过
            //直接返回缓存的短域名
            shortUrl = UrlCache.getUrl(md5);
        }else{
            shortUrl = ShortUrlUtil.generateShortUrl(url, md5Key);
            //缓存md5与短连接的关系
            UrlCache.putUrl(md5,shortUrl);
            //缓存短链接与长链接的关系
            UrlCache.put(shortUrl,url);
        }
        //返回短链接
        return shortUrl;
    }

    public String getOriginUrl(String shortUrl) {

        String url = UrlCache.get(shortUrl);
        if (StrUtil.isEmpty(url)) {
            log.warn("对应url {},没有找到原链接", shortUrl);
            throw new ServerException(404, "抱歉，原链接已过期销毁");
        }
        return url;
    }
}
