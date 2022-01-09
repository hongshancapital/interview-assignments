package com.tizzy.business.service;

import com.tizzy.business.dto.UrlLongRequest;
import com.tizzy.business.exception.EntityNotFoundException;
import com.tizzy.business.repository.Url;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ShortUrlServiceTest {

    @InjectMocks
    ShortUrlService shortUrlService;

    @Test
    public void convertToShortUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/dragon707/interview-assignments");
        url.setExpiresDate(System.currentTimeMillis() + 60 * 1000);

        shortUrlService.getShortUrlAndRefreshTime(7L, url.getLongUrl(), url.getExpiresDate());

        UrlLongRequest urlRequest = new UrlLongRequest();
        urlRequest.setLongUrl("https://github.com/dragon707/interview-assignments");

        Assert.assertEquals("h", shortUrlService.convertToShortUrl(urlRequest.getLongUrl(), urlRequest.getExpiresTime()));
    }

    @Test
    public void getOriginalUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/dragon707/interview-assignments");
        url.setExpiresDate(System.currentTimeMillis() + 60 * 1000);

        shortUrlService.getShortUrlAndRefreshTime(7L, url.getLongUrl(), url.getExpiresDate());
        try {
            Assert.assertEquals("https://github.com/dragon707/interview-assignments", shortUrlService.getOriginalUrl("h"));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOriginalUrlTestException() {
        Url url = new Url();
        url.setLongUrl("https://github.com/dragon707/interview-assignments");
        url.setExpiresDate(System.currentTimeMillis() + 60 * 1000);

        shortUrlService.getShortUrlAndRefreshTime(7L, url.getLongUrl(), url.getExpiresDate());
        try {
            Assert.assertEquals("https://github.com/dragon707/interview-assignments", shortUrlService.getOriginalUrl("t"));
        } catch (EntityNotFoundException e) {
            Assert.assertEquals("original link not found!", e.getMessage());
        }
    }

}
