package com.moonciki.interview.service.url.impl;

import com.moonciki.interview.commons.base.BaseServiceImpl;
import com.moonciki.interview.commons.constant.GlobalConstant;
import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.commons.tools.RedisCacheUtil;
import com.moonciki.interview.service.sys.SystemService;
import com.moonciki.interview.service.url.ShortUrlService;
import com.moonciki.interview.utils.CommonUtil;
import com.moonciki.interview.utils.ShortUrlGenerator;
import com.moonciki.interview.vo.url.ShortUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShortUrlServiceImpl extends BaseServiceImpl implements ShortUrlService {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    private boolean shortUrlExist(String shortUrl){
        String urlKey = CommonUtil.joinRedisKey(GlobalConstant.RedisKey.short_url_alias, shortUrl);
        return redisCacheUtil.hasKey(urlKey);
    }

    private ShortUrlVo getUrlByMd5(String urlMd5){
        String urlKey = CommonUtil.joinRedisKey(GlobalConstant.RedisKey.short_url_md5, urlMd5);
        ShortUrlVo shortUrlVo = (ShortUrlVo)redisCacheUtil.get(urlKey);
        return shortUrlVo;
    }

    private void circleGeneratShort(ShortUrlVo shortUrlVo, List<String> shorList, int partNum){
        String shortUrl = null;
        //2. 如果全部冲突，则尝试6位
        for(int i = 0; i < shorList.size() - partNum; i++){
            shortUrlVo.increaseTryCount();

            List<String> subUrlList = shorList.subList(i, i + partNum + 1);
            String middleShort = StringUtils.join(subUrlList, "");

            if(shortUrlExist(middleShort)){
                continue;
            }
            shortUrl = middleShort;
            break;
        }
        shortUrlVo.setShortUrl(shortUrl);
    }

    private void saveShortUrl(ShortUrlVo shortUrlVo){

        String aliasKey = CommonUtil.joinRedisKey(GlobalConstant.RedisKey.short_url_alias, shortUrlVo.getShortUrl());
        String md5Key = CommonUtil.joinRedisKey(GlobalConstant.RedisKey.short_url_md5, shortUrlVo.getUrlMd5());

        redisCacheUtil.set(aliasKey, shortUrlVo);
        redisCacheUtil.set(md5Key, shortUrlVo);
    }

    @Override
    public ShortUrlVo createShort(String fullUrl) {
        String urlMd5 = DigestUtils.md5Hex(fullUrl);
        ShortUrlVo shortUrlVo = this.getUrlByMd5(urlMd5);

        if(shortUrlVo != null){
            return shortUrlVo;
        }

        shortUrlVo = new ShortUrlVo();
        shortUrlVo.setFullUrl(fullUrl);
        shortUrlVo.setUrlMd5(urlMd5);

        String[] shortArray = ShortUrlGenerator.shortUrl(fullUrl);

        List<String> shorList = ShortUrlGenerator.splitShort(shortArray);

        for(int i =0; i <= 3; i++){
            this.circleGeneratShort(shortUrlVo, shorList, i);

            if(shortUrlVo.shortNotBlank()){
                break;
            }
        }

        this.saveShortUrl(shortUrlVo);
        return shortUrlVo;
    }

    @Override
    public ShortUrlVo getFullUrl(String shortUrl) {
        String urlKey = CommonUtil.joinRedisKey(GlobalConstant.RedisKey.short_url_alias, shortUrl);
        ShortUrlVo shortUrlVo = (ShortUrlVo)redisCacheUtil.get(urlKey);
        return shortUrlVo;
    }
}
