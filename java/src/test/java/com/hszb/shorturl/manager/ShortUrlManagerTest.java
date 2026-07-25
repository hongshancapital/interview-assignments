package com.hszb.shorturl.manager;

import com.hszb.shorturl.BaseTestSupport;
import com.hszb.shorturl.model.ShortUrlResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/25 12:06 下午
 * @Version: 1.0
 * @Description:
 */

public class ShortUrlManagerTest extends BaseTestSupport {

    @Autowired
    private ShortUrlManager shortUrlManager;

    @Test
    public void transferUrl1 () {
        ShortUrlResult shortUrlResult = shortUrlManager.transferShortUrl("http://localhost:8080/test");
        assert null != shortUrlResult;
    }

    @Test
    public void transferUrl2 () {
        try {
            shortUrlManager.transferShortUrl(null);
        } catch (Exception e) {

        }
    }
}
