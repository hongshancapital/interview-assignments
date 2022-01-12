package com.demo.service.impl;

import com.demo.dto.ResponseDTO;
import com.demo.service.CacheService;
import com.demo.service.DomianNamesService;
import com.demo.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author syd
 * @description
 * @date 2022/1/12
 */
@Service
@Slf4j
public class DomianNamesServiceImpl implements DomianNamesService {
    @Autowired
    CacheService cacheService;

    @Override
    public ResponseDTO shorten(String longerUrl) {
        if (longerUrl == null || "".equals(longerUrl)) {
            return ResponseDTO.error("长域名链接不能为空");
        } else {
            String host = getRequestDomain(longerUrl);
            if (StringUtils.endsWithIgnoreCase(longerUrl, "/")) {
                longerUrl = longerUrl.substring(0, longerUrl.length() - 1);
            }
            String md5Str = UUIDUtil.MD5Str(longerUrl);
            String shorten = cacheService.get(md5Str);
            if (shorten != null && !"".equals(shorten)) {
                return ResponseDTO.ok().data(shorten);
            }
            String uuid8 = UUIDUtil.createUUID8();
            String shortUrl = host + uuid8;
            log.info("生成short url :{}", shortUrl);
            cacheService.set(md5Str, shortUrl);
            cacheService.set(shortUrl, longerUrl);
            return ResponseDTO.ok().data(shortUrl);
        }
    }

    @Override
    public ResponseDTO longer(String shortenUrl) {
        if (shortenUrl == null || "".equals(shortenUrl)) {
            return ResponseDTO.error("短域名链接不能为空");
        } else {
            if (StringUtils.endsWithIgnoreCase(shortenUrl, "/")) {
                shortenUrl = shortenUrl.substring(0, shortenUrl.length() - 1);
            }
            String longerUrl = cacheService.get(shortenUrl);
            return ResponseDTO.ok().data(longerUrl);
        }
    }

    /**
     * 获取URL域名部分
     *
     * @param longerUrl
     * @return
     */
    private static String getRequestDomain(String longerUrl) {
        String protocol = longerUrl.split("//")[0];
        StringBuffer sb = new StringBuffer(protocol);
        String domainUrl = longerUrl.split("//")[1];
        String domain = domainUrl.split("/")[0];
        sb.append("//").append(domain).append("/");
        return sb.toString();
    }
}
