/**
 * @(#)UrlHandleBiz.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.application.biz;

import com.example.demo.application.*;
import com.example.demo.constant.DemoErrorEnum;
import com.example.demo.utils.UrlUtils;
import com.google.common.base.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 张三
 */
@Component
public class UrlHandleBiz {

    @Resource
    private UrlDao urlDao;

    @Resource
    private UrlTransferService urlTransferService;

    public String transferLongUrl(String longUrl) {
        Preconditions.checkState(StringUtils.isNotBlank(longUrl), DemoErrorEnum.PARAM_ERROR);

        String key = urlTransferService.transferToShortUrlKey(longUrl);

        String value = urlDao.queryLongUrl(key);
        if (StringUtils.isEmpty(value)) {
            urlDao.save(key, longUrl);
        }

        return UrlUtils.generateShortUrl(key);
    }

    public String getLongUrl(String shortUrl) {
        Preconditions.checkState(StringUtils.isNotBlank(shortUrl), DemoErrorEnum.PARAM_ERROR);
        String key = UrlUtils.getKey(shortUrl);
        return urlDao.queryLongUrl(key);
    }

}
