package com.mortimer.shortenurl.controller;

import com.mortimer.shortenurl.model.ResponseModel;
import com.mortimer.shortenurl.service.UrlShortenService;
import com.mortimer.shortenurl.vo.LongUrl;
import com.mortimer.shortenurl.vo.UrlMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UrlShortenControllerTest {
    @Mock
    private UrlShortenService urlShortenService;

    @InjectMocks
    private UrlShortenController urlShortenController;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createShortUrl() {
        String longUrl = "http://www.testlong.com";
        String shortUrl = "http://localhost:8888/XYZ";
        LongUrl requestBody = new LongUrl();
        requestBody.setUrl(longUrl);

        when(urlShortenService.createShortUrl(requestBody.getUrl())).thenReturn(shortUrl);
        ResponseModel<UrlMapping> urlMapping = urlShortenController.createShortUrl(requestBody);
        Assertions.assertEquals(shortUrl, urlMapping.getData().getShortUrl());
    }

    @Test
    public void test_getLongUrl() {
        String longUrl = "http://www.testlong.com";
        String shortUrl = "XYZ";

        when(urlShortenService.getLongUrl(shortUrl)).thenReturn(longUrl);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8888/" + shortUrl));
        ResponseModel<UrlMapping> urlMapping = urlShortenController.getLongUrl(shortUrl);
        Assertions.assertEquals(longUrl, urlMapping.getData().getLongUrl());

        shortUrl = "ABC";
        urlMapping = urlShortenController.getLongUrl(shortUrl);
        Assertions.assertNull(urlMapping.getData());
    }
}
