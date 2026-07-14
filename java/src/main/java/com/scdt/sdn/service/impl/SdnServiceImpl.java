/**
 * Project: scdt-sdn
 * File created at 2022/3/13 21:18
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package com.scdt.sdn.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdt.sdn.exceptions.NotFoundException;
import com.scdt.sdn.service.SdnService;
import com.scdt.sdn.util.EncodeUtil;
import com.scdt.sdn.util.SnowUtils;

/**
 * 功能描述
 *
 * @author donghang
 * @version 1.0
 * @type SdnServiceImpl
 * @date 2022/3/13 21:18
 */
@Service
public class SdnServiceImpl implements SdnService {
    @Autowired
    private SnowUtils snowUtils;
    @Autowired
    private EncodeUtil encodeUtil;

    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    @Override
    public String encode(String url) {
        Long id = snowUtils.nextId();
        String encodedId = encodeUtil.encode(id);
        map.put(encodedId, url);
        return encodedId;
    }

    @Override
    public String decode(String code) {
        String value = map.get(code);
        if (null == value) {
            throw new NotFoundException("url : " + code + " , not found!");
        }
        return value;
    }
}
