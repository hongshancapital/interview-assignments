package com.wangxiao.shortlink.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShortLinkResourceTest {
    ShortLinkResource shortLinkResource = new ShortLinkResource();

    @Test
    void testEncodeUrl() {
        String result = shortLinkResource.encode("longLink").getData();
        Assertions.assertEquals("", result);
    }

    @Test
    void testDecodeUrl() {
        String result = shortLinkResource.decode("shortLink").getData();
        Assertions.assertEquals("", result);
    }
}

