package com.hszb.shorturl.generate;

import com.hszb.shorturl.manager.generate.IdShortUrlGenerator;
import com.hszb.shorturl.manager.generate.Md5ShortUrlGenerator;
import org.junit.jupiter.api.Test;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 2:19 下午
 * @Version: 1.0
 * @Description:
 */


public class ShortCodeGeneratorTest {

    @Test
    public void idGeneratorTest () {
        IdShortUrlGenerator idShortUrlGenerator = new IdShortUrlGenerator();
        String code = idShortUrlGenerator.generateCode("http://hszb.com/shortUrl/transfer");
        assert null != code && code.length() < 8;
    }

    @Test
    public void randomGeneratorTest () {
        Md5ShortUrlGenerator randomShortUrlGenerator = new Md5ShortUrlGenerator();
        String code = randomShortUrlGenerator.generateCode("http://hszb.com/shortUrl/transfer");
        assert null != code && code.length() < 8;
    }
}
