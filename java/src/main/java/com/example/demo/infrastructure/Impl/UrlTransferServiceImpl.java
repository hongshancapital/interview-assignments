/**
 * @(#)UrlTransferServiceImpl.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.infrastructure.Impl;

import com.example.demo.application.UrlTransferService;
import com.example.demo.utils.UrlUtils;
import org.springframework.stereotype.Component;

/**
 * @author 张三
 */
@Component
public class UrlTransferServiceImpl implements UrlTransferService {

    @Override
    public String transferToShortUrl(String longUrl) {
        return UrlUtils.generateKey(longUrl);
    }
}