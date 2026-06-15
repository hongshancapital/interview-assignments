package com.skyscreen.urldemo.controller;

import com.skyscreen.urldemo.service.ConvertUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UrlControllerTest {

    @InjectMocks
    UrlController urlController;

    @Mock
    private ConvertUrlService convertUrlService;

    @Test
    public void getLongUrl() {
        Mockito.when(convertUrlService.convertToLongUrl(Mockito.anyString())).thenReturn("http://www.baidu.com");
        Assertions.assertNotNull(urlController.getLongUrl("http://c.cn/abcdefgh"));
    }

    @Test
    public void getShortUrl() {
        Mockito.when(convertUrlService.convertToShortUrl(Mockito.anyString())).thenReturn("http://c.cn/abcdefgh");
        Assertions.assertNotNull(urlController.getShortUrl("http://www.baidu.com"));
    }

}
