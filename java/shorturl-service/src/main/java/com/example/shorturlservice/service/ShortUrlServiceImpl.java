package com.example.shorturlservice.service;

import com.example.shorturlservice.domain.BStatusCode;
import com.example.shorturlservice.domain.BizException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description 短域名的存储和读取逻辑实现
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    BiMap<String, String> urlWarehouseMap = HashBiMap.create();


    @Override
    public String saveLongUrl(String longUrl) throws BizException {
        if (!StringUtils.hasLength(longUrl)) {
            throw new BizException(BStatusCode.PARAM_NULL.getCode(), BStatusCode.PARAM_NULL.getDes());
        }
        if (longUrl.length() > 128) {
            throw new BizException(BStatusCode.PARAM_LENGTH_LIMIT.getCode(), BStatusCode.PARAM_LENGTH_LIMIT.getDes());
        }
        //幂等
        if (urlWarehouseMap.containsKey(longUrl)) {
            return urlWarehouseMap.get(longUrl);
        }

        //监控堆内存使用率
        Runtime r = Runtime.getRuntime();
        long total = r.totalMemory() / (1024 * 1024);
        long free = r.freeMemory() / (1024 * 1024);
        float freeRate = (float) free / total * 100;
        if (freeRate < 5.0f) {
            throw new BizException(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
        }

        //返回一组4个短域名，循环添加，防止短域名碰撞，添加失败
        String shortUrl = "";
        String[] urlRes = ShortUrlGenerator.shortUrl(longUrl);
        for (String url : urlRes) {
            try {
                urlWarehouseMap.put(longUrl, url);
                shortUrl = url;
                break;
            } catch (IllegalArgumentException e) {
                //日志略
            }
        }

        if (!StringUtils.hasLength(shortUrl)) {
            throw new BizException(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
        }

        return shortUrl;
    }

    @Override
    public String getShortUrl(String shortUrl) {
        return urlWarehouseMap.inverse().get(shortUrl);
    }
}
