package com.hongshang.shorturlweb;

import com.hongshang.common.ComResult;
import com.hongshang.shorturlinterface.IShortUrlService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 短地址服务CONTROL测试类
 *
 * @author kobe
 * @date 2021/12/19
 */
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(JMockit.class)
//@SpringBootTest
public class ShortUrlControlTest {

    public static final String LONG_URL = "https://mp.weixin.qq.com/s?__biz=MzI3MTEwODc5Ng==&mid=2650860717&idx=1&sn=d41494d71f3ef2298d02000590bda6cb&chksm=f132937ec6451a68832461cd25bc35c03bd23c17b45b3ea622bf960653a006a5e5d0f68d1396&token=1991831939&lang=zh_CN#rd";
    public static final String SHORT_URL = "adzwz2e4";

    @Tested
    private ShortUrlControl shortUrlControl;

    @Injectable
    private IShortUrlService shortUrlService;

    /**
     * 测试 “短域名存储接口：接受长域名信息，返回短域名信息”  服务
     * 传过长地址，返回短地址的的情况
     */
    @Test
    public void testTransformToShortUrl() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                shortUrlService.transformToShortUrl(LONG_URL);
                result = shortUrl;
            }
        };
        ComResult comResult = shortUrlControl.transformToShortUrl(longUrl);
        boolean expectIsSucess = true;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertEquals(shortUrl, comResult.getResult().toString());
    }

    /**
     * 测试 “短域名存储接口：接受长域名信息，返回短域名信息”  服务
     * 入参为null的的情况
     */
    @Test
    public void testTransformToShortUrlByNull() {
        String longUrl = null;
        ComResult comResult = shortUrlControl.transformToShortUrl(longUrl);
        boolean expectIsSucess = false;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertNull( comResult.getResult());
    }

    /**
     * 测试 “短域名存储接口：接受长域名信息，返回短域名信息”  服务
     * 调用业务服务接口抛出异常的情况
     */
    @Test
    public void testTransformToShortUrlException() {
        String longUrl = LONG_URL;
        String errorMessage = "System error!";

        new Expectations() {
            {
                shortUrlService.transformToShortUrl(longUrl);
                result = new RuntimeException(errorMessage);
            }
        };
        ComResult comResult = shortUrlControl.transformToShortUrl(longUrl);
        boolean expectIsSucess = false;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertEquals(errorMessage, comResult.getMessage());
        assertNull( comResult.getResult());
    }

    /**
     * 测试 “短域名读取接口：接受短域名信息，返回长域名信息”  服务
     * 传过长地址，返回短地址的的情况
     */
    @Test
    public void testGetOriginalUrlByShortUrl() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                shortUrlService.getLongUrlByShortUrl(shortUrl);
                result = longUrl;
            }
        };
        ComResult comResult = shortUrlControl.getOriginalUrlByShortUrl(shortUrl);
        boolean expectIsSucess = true;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertEquals(longUrl, comResult.getResult().toString());
    }

    /**
     * 测试 “短域名读取接口：接受短域名信息，返回长域名信息”  服务
     *  入参为null的情况
     */
    @Test
    public void testGetOriginalUrlByShortUrlNull() {
        String shortUrl = null;
        ComResult comResult = shortUrlControl.getOriginalUrlByShortUrl(shortUrl);
        boolean expectIsSucess = false;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertNull( comResult.getResult());
    }

    /**
     * 测试 “短域名读取接口：接受短域名信息，返回长域名信息”  服务
     *  返回lognUrl为null的情况
     */
    @Test
    public void testGetOriginalUrlByShortUrlNullLongUrl() {
        String longUrl = null;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                shortUrlService.getLongUrlByShortUrl(shortUrl);
                result = longUrl;
            }
        };
        ComResult comResult = shortUrlControl.getOriginalUrlByShortUrl(shortUrl);
        boolean expectIsSucess = false;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertNull( comResult.getResult());
    }


    /**
     * 测试 “短域名读取接口：接受短域名信息，返回长域名信息”  服务
     * 调用业务接口抛出异常的情况
     */
    @Test
    public void testGetOriginalUrlByShortUrlException() {
        String shortUrl = SHORT_URL;
        String errorMessage = "System error!";

        new Expectations() {
            {
                shortUrlService.getLongUrlByShortUrl(shortUrl);
                result = new RuntimeException(errorMessage);
            }
        };
        ComResult comResult = shortUrlControl.getOriginalUrlByShortUrl(shortUrl);
        boolean expectIsSucess = false;
        assertEquals(expectIsSucess, comResult.getIsSuccess());
        assertEquals(errorMessage, comResult.getMessage());
        assertNull( comResult.getResult());
    }
}