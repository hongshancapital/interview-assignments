package com.wangxiao.shortlink.applicaiton.impl;

import com.wangxiao.shortlink.applicaiton.ShortLinkService;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {
    @Override
    public String encodeUrl(String longLink) {
        return "";
    }

    @Override
    public String decodeUrl(String shortLink) {
        return "";
    }
}
