import com.xg.shorturl.ShortUrlApplication;
import com.xg.shorturl.common.BaseErrorCode;
import com.xg.shorturl.common.BaseResponse;
import com.xg.shorturl.common.BizException;
import com.xg.shorturl.controller.ShortUrlController;
import com.xg.shorturl.request.GetShortUrlRequest;
import com.xg.shorturl.request.QueryOriginalUrlRequest;
import com.xg.shorturl.service.ShortUrlService;
import com.xg.shorturl.utils.ShortUrlGenerator;
import com.xg.shorturl.vo.OriginalUrlInfoVO;
import com.xg.shorturl.vo.ShortUrlInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class)
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private ShortUrlController shortUrlController;

    /**
     * 生成短链接，查询已保存的短链接
     */
    @Test
    public void testGenerateShortUrl() {
        String defaultDomain = "http://default.domain/";
        String originalUrl = "http://test.original.url";
        String shortUrl = shortUrlService.queryOrGenerateShortUrl(originalUrl);
        int length = shortUrl.length() - defaultDomain.length();
        Assert.assertEquals(8, length);

        String shortUrl2 = shortUrlService.queryOrGenerateShortUrl(originalUrl);
        Assert.assertEquals(shortUrl, shortUrl2);
    }

    /**
     * 查询原始链接
     */
    @Test
    public void testQueryOriginalUrl() {
        String originalUrl = "http://test.original.url";
        String shortUrl = shortUrlService.queryOrGenerateShortUrl(originalUrl);

        String queryOriginalUrl = shortUrlService.queryOriginalUrl(shortUrl);
        Assert.assertEquals(originalUrl, queryOriginalUrl);

        String notExistShortUrl = "http://default.domain/12345678";
        queryOriginalUrl = shortUrlService.queryOriginalUrl(notExistShortUrl);
        Assert.assertNull(queryOriginalUrl);
    }

    /**
     * url合法性
     */
    @Test
    public void testInvalidUrl() {

        GetShortUrlRequest getShortUrlRequest = new GetShortUrlRequest();
        getShortUrlRequest.setOriginalUrl("xxx");
        BaseResponse<ShortUrlInfoVO> response;
        Exception exception = new Exception();
        try {
            shortUrlController.getShortUrl(getShortUrlRequest);
        } catch (BizException ex) {
            exception = ex;
        }
        Assert.assertEquals("url不合法", exception.getMessage());

        getShortUrlRequest.setOriginalUrl("http://1234");
        response = shortUrlController.getShortUrl(getShortUrlRequest);
        Assert.assertEquals(BaseErrorCode.SUCCESS.getCode(), response.getErrorCode().longValue());

        getShortUrlRequest.setOriginalUrl("https://123/xx/a-0-X");
        response = shortUrlController.getShortUrl(getShortUrlRequest);
        Assert.assertEquals(BaseErrorCode.SUCCESS.getCode(), response.getErrorCode().longValue());

        QueryOriginalUrlRequest queryOriginalUrlRequest = new QueryOriginalUrlRequest();
        queryOriginalUrlRequest.setShortUrl("http:1234");
        BaseResponse<OriginalUrlInfoVO> response2;
        Exception exception2 = new Exception();
        try {
            shortUrlController.queryOriginalUrl(queryOriginalUrlRequest);
        } catch (BizException ex) {
            exception2 = ex;
        }
        Assert.assertEquals("url不合法", exception2.getMessage());

        queryOriginalUrlRequest.setShortUrl("https://1234");
        response2 = shortUrlController.queryOriginalUrl(queryOriginalUrlRequest);
        Assert.assertEquals(BaseErrorCode.SUCCESS.getCode(), response2.getErrorCode().longValue());

    }

    /**
     * lruCache未超过最大容量
     */
    @Test
    public void testLRUCache() {
        String originalUrl = "http://test.lru.cahche";
        String shortUrl = shortUrlService.queryOrGenerateShortUrl(originalUrl);
        for (int i = 0; i < 99; i++) {
            shortUrlService.queryOrGenerateShortUrl(originalUrl+i);
        }
        String queryOriginalUrl = shortUrlService.queryOriginalUrl(shortUrl);
        Assert.assertEquals(originalUrl, queryOriginalUrl);
    }

    /**
     * lruCache超过最大容量
     */
    @Test
    public void testLRUCache2() {
        String originalUrl = "http://test.lru.cahche";
        String shortUrl = shortUrlService.queryOrGenerateShortUrl(originalUrl);
        for (int i = 0; i < 100; i++) {
            shortUrlService.queryOrGenerateShortUrl(originalUrl+i);
        }
        String queryOriginalUrl = shortUrlService.queryOriginalUrl(shortUrl);
        Assert.assertNull(queryOriginalUrl);
    }

    /**
     * Mockito测试短链接重复性问题
     */
    @Test
    public void testDuplicate() {
        MockedStatic<ShortUrlGenerator> mockedGenerator = Mockito.mockStatic(ShortUrlGenerator.class);
        mockedGenerator.when(() -> ShortUrlGenerator.generate(anyString())).thenReturn("12345678");

        GetShortUrlRequest getShortUrlRequest = new GetShortUrlRequest();
        getShortUrlRequest.setOriginalUrl("http://test-dumplicate-link");
        BaseResponse<ShortUrlInfoVO> response = shortUrlController.getShortUrl(getShortUrlRequest);
        Assert.assertEquals("http://default.domain/12345678", response.getResult().getShortUrl());

        Exception exception = new Exception();
        try {
            getShortUrlRequest.setOriginalUrl("http://test-dumplicate-link2");
            shortUrlController.getShortUrl(getShortUrlRequest);
        } catch (BizException ex) {
            exception = ex;
        }
        Assert.assertEquals("短链接重复，重试次数超过限制", exception.getMessage());
    }
}
