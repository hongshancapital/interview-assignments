package com.hszb.shorturl.storage;

import com.hszb.shorturl.manager.storage.ShortUrlStorage;
import org.junit.Test;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/24 10:40 上午
 * @Version: 1.0
 * @Description:
 */


public class ShortUrlStorageTest {

    ShortUrlStorage shortUrlStorage = ShortUrlStorage.getInstance();

    @Test
    public void queryShortUrl () {
        shortUrlStorage.queryShortUrl(null);
    }

    @Test
    public void queryShortUrl2 () {
        shortUrlStorage.queryShortUrl("Test");
    }


    @Test
    public void queryLongUrl1 () {
        shortUrlStorage.queryLongUrl(null);
    }

    @Test
    public void queryLongUrl2 () {
        shortUrlStorage.queryLongUrl("Test");
    }
}
