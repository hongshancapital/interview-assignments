package com.shortlink.service;

import com.shortlink.common.BusinessException;
import com.shortlink.common.Constants;
import com.shortlink.common.ShortLinkCache;
import com.shortlink.util.ShortUrlEnCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShortLinkServiceImpl {
    @Autowired
    private ShortLinkCache shortLinkCache;

    /**
     * 根据长域名生成短域名，返回短域名信息，并存储映射关系
     * @param longLink
     * @return
     */
    public String saveShortLink(String longLink){
        if (StringUtils.isEmpty(longLink)) {
            throw new BusinessException("长域名不能为空");
        }
        if (longLink.length() > Constants.LONG_LINK_MAX_SIZE) {
            throw new BusinessException("长域名超出长度限制: " + Constants.LONG_LINK_MAX_SIZE);
        }
        String shortUrl = ShortUrlEnCoder.encodeLongUrl(longLink);
        shortLinkCache.getCache().put(shortUrl, longLink);
        return shortUrl;
    }

    /**
     * 输入短域名，返回长域名
     * @param shortLink
     * @return
     */
    public String convert2LongLink(String shortLink){
        String longLink = null;
        try{
            longLink = shortLinkCache.getCache().get(shortLink);
        } catch (Exception e) {
            throw new BusinessException("查询缓存异常，请稍后再试。短域名: " + shortLink, e);
        }

        if (StringUtils.isEmpty(longLink)) {
            throw new BusinessException("未找到对应的长域名，可能缓存过期或输入有误 " + shortLink);
        }

        return longLink;
    }
}
