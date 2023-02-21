package com.wangxiao.shortlink.resource;

import com.wangxiao.shortlink.domain.shortlink.LinkPairRepository;
import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.Result;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ShortLinkResourceTest {
    @Resource
    ShortLinkResource shortLinkResource;

    @Resource
    private ShortLinkProperties shortLinkProperties;

    @Resource
    private LinkPairRepository linkPairRepository;

    @AfterEach
    void cleanStore() {
        Files.delete(new File(shortLinkProperties.getStorePath()));
        linkPairRepository.clear();
    }

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
    void testEncodeEmpty() {
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
    void testDecodeEmpty() {
        Result<String> result = shortLinkResource.decode("");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.PARAMS_ILLEGAL.getCode());
    }

    @Test
    void testDecodeFail() {
        Result<String> result = shortLinkResource.decode("%");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.PARAMS_ILLEGAL.getCode());
    }

    @Test
    void testDecodeNotExit() {
        Result<String> result = shortLinkResource.decode(shortLinkProperties.getMachineId() + "1");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCode(), ErrorEnum.LINK_NOT_EXISTS.getCode());
    }

}

