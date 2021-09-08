package com.moonciki.interview.service.sys.impl;

import com.moonciki.interview.commons.base.BaseServiceImpl;
import com.moonciki.interview.commons.tools.RedisCacheUtil;
import com.moonciki.interview.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl extends BaseServiceImpl implements SystemService {

    @Autowired
    RedisCacheUtil redisCacheUtil;

    @Override
    public void helloWorld() {

        redisCacheUtil.set("aaaaaaaaa", 1);

        System.out.println("hello world");

    }

}
