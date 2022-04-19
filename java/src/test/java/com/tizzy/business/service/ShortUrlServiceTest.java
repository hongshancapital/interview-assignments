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

        UrlLongRequest urlRequest1 = new UrlLongRequest();
        urlRequest1.setLongUrl("https://github.com/dragon707/interview-assignments");
        Assert.assertEquals("h", shortUrlService.convertToShortUrl(urlRequest1.getLongUrl(), urlRequest1.getExpiresTime()));

        UrlLongRequest urlRequest2 = new UrlLongRequest();
        urlRequest2.setLongUrl("https://www.baidu.com/s?wd=%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC&rsv_spt=1&rsv_iqid=0x951b4ce60003603b&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=1&rsv_dl=tb&oq=hongshan&rsv_btype=t&inputT=3073&rsv_t=f3b4A2eoddlFRsaO2g2cKQjW4ay4XxA5YviFCiZe9KrYespIrVd2yNwBUfvlebE59d60&rsv_pq=fbc47fc80000aead&rsv_sug3=20&rsv_sug1=12&rsv_sug7=100&rsv_sug2=0&rsv_sug4=3073");
        Assert.assertEquals("i", shortUrlService.convertToShortUrl(urlRequest2.getLongUrl(), urlRequest2.getExpiresTime()));

        urlRequest2.setExpiresTime(System.currentTimeMillis() + 60 * 1000);
        Assert.assertEquals("i", shortUrlService.convertToShortUrl(urlRequest2.getLongUrl(), urlRequest2.getExpiresTime()));
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
            Assert.assertEquals(0, 1);
        }
    }

    @Test
    public void getOriginalUrlExceptionTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/dragon707/interview-assignments");
        url.setExpiresDate(System.currentTimeMillis() + 60 * 1000);

        shortUrlService.getShortUrlAndRefreshTime(7L, url.getLongUrl(), url.getExpiresDate());
        try {
            shortUrlService.getOriginalUrl("t");
        } catch (EntityNotFoundException e) {
            Assert.assertEquals("original link not found!", e.getMessage());
        }
    }

}
