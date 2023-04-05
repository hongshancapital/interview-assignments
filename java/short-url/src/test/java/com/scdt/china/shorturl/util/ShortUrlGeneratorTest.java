package com.scdt.china.shorturl.util;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author：costa
 * @date：Created in 2022/4/14 16:36
 */
@SpringBootTest()
public class ShortUrlGeneratorTest {
    String url = "https://test.com/test";

    @Test
    public void testGetShortUrl() {
        String shortUrl = ShortUrlGenerator.shortUrl(url);
        Assert.notBlank(shortUrl);
        Assert.isTrue(shortUrl.length() == 6);

    }
}
