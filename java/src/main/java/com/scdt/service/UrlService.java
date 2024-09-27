package com.scdt.service;

import com.scdt.exception.CustomException;
import com.scdt.util.CacheUtil;
import com.scdt.util.ShortUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlService {

    @Value("${shortUrl}")
    private String baseShortUrl = "http://a.com";

    @Value("${randomToken}")
    private String keyPrefix = "JtCLFWfl";

    public String long2Short(String url) {
        String s = CacheUtil.get(url);
        if (StringUtils.isEmpty(s)) {
            try {
                s = ShortUrlUtil.encode(url);
                s = new StringBuilder().append(baseShortUrl).append("/").append(s).toString();
                CacheUtil.put(s, url);
            } catch (Exception e) {
                log.error("短连接生成异常 ", e);
                throw new CustomException("短连接生成异常，请稍后重试");
            }
        }
        return s;
    }

    public String short2Long(String url) {
        String decodeStr = CacheUtil.get(url);
        if (StringUtils.isEmpty(decodeStr)) {
            throw new CustomException("短连接已经失效");
        }
        return decodeStr;
    }
}
