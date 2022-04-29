package com.hszb.shorturl.generate;

import com.hszb.shorturl.manager.generate.ShortUrlGeneratorFactory;
import org.junit.Test;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/24 10:49 上午
 * @Version: 1.0
 * @Description:
 */

public class ShortUrlGeneratorFactoryTest {

    @Test
    public void create () {
        assert null != ShortUrlGeneratorFactory.create(null);
    }

    @Test
    public void create2 () {
        assert null != ShortUrlGeneratorFactory.create("id");
    }

    @Test
    public void create3 () {
        assert null != ShortUrlGeneratorFactory.create("md5");
    }
}
