package com.liu.shorturl.service.impl;

import com.liu.shorturl.exception.BusinessException;
import com.liu.shorturl.service.ShortUrlServicce;
import com.liu.shorturl.utils.GuavaCacheUtils;
import com.liu.shorturl.utils.IdWorker;
import com.liu.shorturl.utils.NumericConvertUtils;
import com.liu.shorturl.utils.SnowFlakeIdWorker;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * Description： 短域名服务接口实现
 * Author: liujiao
 * Date: Created in 2021/11/11 18:47
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlServicce {

    private static final String PREFIX_URL = "http://www.sds.com/";

    @Override
    public String getUrlByShortUrl(String shortUrl) {
        if(StringUtils.isEmpty(shortUrl)) {
            return null;
        }
        //获取短域名链接最后的字符
        String suffixUrl = shortUrl.split("/")[shortUrl.split("/").length - 1];
        if(StringUtils.isEmpty(suffixUrl)) {
            return null;
        }
        Object longUrl = GuavaCacheUtils.get(suffixUrl);
        if(Objects.isNull(longUrl)) {
            return null;
        }
        return (String) longUrl;
    }

    @Override
    public String addShortUrlByUrl(String url) {
        if(StringUtils.isEmpty(url)) {
            return null;
        }

        //判断缓存中是否已经存在该字符对应的长链接,如果有，直接返回
        if(GuavaCacheUtils.containsValue(url)) {
            return PREFIX_URL + GuavaCacheUtils.getKeyByValue(url);
        }

        //生成短链接后缀唯一ID
        IdWorker worker = new SnowFlakeIdWorker(1, 1, 1);
        long id = (long) worker.nextId();

        //根据生成的ID获取对应的62进制字符
        String idStr = NumericConvertUtils.toOtherNumberSystem(id, 62);

        //缓存中存储对应的ID62位字符与长链接的对应关系
        GuavaCacheUtils.put(idStr, url);
        return PREFIX_URL + idStr;
    }
}
