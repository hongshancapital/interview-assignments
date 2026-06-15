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

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    //短链接前置
    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.md5Key}")
    private String md5Key;

    private static final String URL_FORMAT = "%s%s";

    /**
    * @description: 长链接转短链接
    * @param url  长链接地址
    * @return: 短链接地址
    * @author: Leo
    * @date: 2022/3/2 16:58
    */
    @Override
    public String shorterUrl(String url) {
        //短链接编码
        String shortCode;
        final String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
        //如果长域名已经请求过
        if(UrlCache.containsUrl(md5)){
            //直接返回缓存的短域名
            shortCode = UrlCache.getUrl(md5);
        }else{
            shortCode = ShortUrlUtil.generateShortUrl(url, md5Key);
            //缓存md5与短连接的关系
            UrlCache.putUrl(md5,shortCode);
            //缓存短链接与长链接的关系
            UrlCache.put(shortCode,url);
        }
        //返回短链接 如：896950f4 短链接到：http://t.cn/896950f4
        return String.format(URL_FORMAT,shortUrlPrefix,shortCode) ;
    }
    /**
    * @description: 通过长链接码找短链接
    * @param shortUrl 短链接
    * @return:
    * @author: Leo
    * @date: 2022/3/2 16:59
    */
    @Override
    public String getOriginUrl(String shortUrl) {

        if(StrUtil.startWith(shortUrl,shortUrlPrefix)){
            String shortCode = StrUtil.subSuf(shortUrl,shortUrlPrefix.length());
            String url = UrlCache.get(shortCode);
            if (StrUtil.isEmpty(url)) {
                log.warn("对应url {},没有找到原链接", shortUrl);
                throw new ServerException(404, "抱歉，原链接已过期销毁");
            }
            return url;
        }else{
            log.warn("对应url {},没有找到原链接", shortUrl);
            throw new ServerException(500, "抱歉，请求地址错误");
        }
    }
}
