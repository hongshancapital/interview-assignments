package com.scdt.service;

import com.scdt.exception.CustomException;
import com.scdt.model.request.UrlRequest;
import com.scdt.util.Base62Util;
import com.scdt.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class UrlService {

    private String baseShortUrl = "http://a.com";

    public String long2Short(String url) {
        String s = CacheUtil.get(url);
        if (StringUtils.isEmpty(s)) {
            try {
                s = Base62Util.encodeBase62(url);
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
