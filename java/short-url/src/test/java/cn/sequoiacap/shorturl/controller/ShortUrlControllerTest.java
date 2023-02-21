package cn.sequoiacap.shorturl.controller;

import cn.sequoiacap.shorturl.exception.StoreException;
import cn.sequoiacap.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ShortUrlControllerTest {
    @MockBean
    private ShortUrlService service;
    @Autowired
    private ShortUrlController controller;

    @Test
    public void testGenerate() throws StoreException {
        String originalUrl = null;
        Response<String> result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("original url is null or empty", result.getMessage());

        originalUrl = "";
        result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("original url is null or empty", result.getMessage());

        originalUrl = "abc";
        result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("original url is invalid", result.getMessage());

        originalUrl = "ftp://www.abc.com";
        result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("protocol of original url is invalid", result.getMessage());

        originalUrl = "https://www.abc.com";
        Mockito.when(service.generate(originalUrl)).thenReturn(null);
        result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.INTERNAL_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("generate short url failed", result.getMessage());

        originalUrl = "https://www.abc.com";
        String shortUrlId = "abc";
        Mockito.when(service.generate(originalUrl)).thenReturn(shortUrlId);
        result = controller.generate(originalUrl);
        Assertions.assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(shortUrlId, result.getData());
    }

    @Test
    public void testGet() throws StoreException {
        String shortUrlId = null;
        Response<String> result = controller.get(shortUrlId);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("short url id is invalid", result.getMessage());

        shortUrlId = "abcdefghijkl";
        result = controller.get(shortUrlId);
        Assertions.assertEquals(ResponseStatus.PARAM_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("short url id is invalid", result.getMessage());

        shortUrlId = "abc";
        Mockito.when(service.get(shortUrlId)).thenReturn(null);
        result = controller.get(shortUrlId);
        Assertions.assertEquals(ResponseStatus.NOT_FOUND.getCode(), result.getCode());
        Assertions.assertEquals("not found", result.getMessage());

        shortUrlId = "abc";
        String originalUrl = "https://www.abc.com";
        Mockito.when(service.get(shortUrlId)).thenReturn(originalUrl);
        result = controller.get(shortUrlId);
        Assertions.assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(originalUrl, result.getData());
    }
}
