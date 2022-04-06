package com.domain.urlshortener.controller;

import com.domain.urlshortener.model.ResponseModel;
import com.domain.urlshortener.service.UrlShortenerService;
import com.domain.urlshortener.vo.ShortUrlCreateRequest;
import com.domain.urlshortener.vo.ShortUrlCreateResponse;
import com.domain.urlshortener.vo.ShortUrlGetRequest;
import com.domain.urlshortener.vo.ShortUrlGetResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 2:10
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlShortenerControllerTest {

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    @Mock
    UrlShortenerService urlShortenerService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createShortUrl() {
        ShortUrlCreateRequest request = new ShortUrlCreateRequest();
        request.setUrl("http://xx.com");

        Mockito.when(urlShortenerService.createShortUrl(request.getUrl())).thenReturn("https://m.dev.com/G8");

        ResponseModel<ShortUrlCreateResponse> responseModel = urlShortenerController.createShortUrl(request);
        Assertions.assertEquals("https://m.dev.com/G8", responseModel.getData().getShortUrl());
    }

    @Test
    public void test_retrieveLongUrl() {
        ShortUrlGetRequest request = new ShortUrlGetRequest();
        request.setUrl("https://m.dev.com/G8");

        Mockito.when(urlShortenerService.getLongUrl(request.getUrl())).thenReturn("http://xx.com");

        ResponseModel<ShortUrlGetResponse> responseModel = urlShortenerController.retrieveLongUrl(request);
        Assertions.assertEquals("http://xx.com", responseModel.getData().getLongUrl());
    }

}
