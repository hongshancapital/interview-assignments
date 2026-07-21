package com.yilong.shorturl.controller;

import com.yilong.shorturl.model.Result;
import com.yilong.shorturl.model.dto.UrlDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlControllerTest {

    @Autowired
    private UrlController urlController;

    @Test
    public void testCreateShortUrl() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl("https://www.vampire.com/url/looooooong/1");
        Result<String> result = urlController.encodeUrl(urlDTO);
        Assertions.assertEquals(true, result.getSuccess());
        Assertions.assertEquals("https://8.cn/1Evr03", result.getData());
        Assertions.assertEquals(null, result.getError());
        Assertions.assertEquals(200, result.getStatus());
    }

    @Test
    public void testGetOriginUrl() {
        UrlDTO longUrl = new UrlDTO();
        longUrl.setUrl("https://www.vampire.com/url/looooooong/1");
        urlController.encodeUrl(longUrl);
        UrlDTO shortUrl = new UrlDTO();
        shortUrl.setUrl("https://8.cn/1Evr03");
        Result<String> result = urlController.decodeUrl(shortUrl);
        Assertions.assertEquals(true, result.getSuccess());
        Assertions.assertEquals("https://www.vampire.com/url/looooooong/1", result.getData());
        Assertions.assertEquals(null, result.getError());
        Assertions.assertEquals(200, result.getStatus());
    }
}
