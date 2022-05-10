package com.wangxiao.shortlink.resource;

import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShortLinkResourceTest {
    @Resource
    ShortLinkResource shortLinkResource;

    @Test
    void testEncodeUrl() {
        String result = shortLinkResource.encode("https://www.baidu.com").getData();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length() == 8);
    }

    @Test
    void testEncodeUrlSame() {
        String result1 = shortLinkResource.encode("https://www.baidu.com").getData();
        String result2 = shortLinkResource.encode("https://www.baidu.com").getData();
        Assertions.assertNotNull(result1);
        Assertions.assertEquals(result1, result2);
    }

    @Test
    void testEncodeFail() {
        Result<String> result = shortLinkResource.encode("");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.PARAMS_ILLEGAL.getCode());
    }

    @Test
    void testEncodeUrlDif() {
        String result1 = shortLinkResource.encode("https://www.hao123.com").getData();
        String result2 = shortLinkResource.encode("https://www.baidu.com").getData();
        Assertions.assertNotNull(result1);
        Assertions.assertNotEquals(result1, result2);
    }

    @Test
    void testDecodeUrl() {
        String url = "https://www.baidu.com/abc";
        String shortLink = shortLinkResource.encode(url).getData();
        String result = shortLinkResource.decode(shortLink).getData();
        Assertions.assertEquals(url, result);
    }

    @Test
    void testDecodeFail() {
        Result<String> result = shortLinkResource.decode("");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.PARAMS_ILLEGAL.getCode());
    }

    @Test
    void testDecodeNotExit() {
        Result<String> result = shortLinkResource.decode("01A");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.LINK_NOT_EXISTS.getCode());
    }
}

