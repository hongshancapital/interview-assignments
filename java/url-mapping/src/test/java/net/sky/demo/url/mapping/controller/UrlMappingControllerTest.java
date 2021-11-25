package net.sky.demo.url.mapping.controller;

import net.sky.demo.url.mapping.bean.GenerateShortUrlRequest;
import net.sky.demo.url.mapping.service.UrlMappingService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UrlMappingControllerTest {

    @Test
    public void long2short() {
        GenerateShortUrlRequest request = new GenerateShortUrlRequest();
        request.setUrl("http://www.xxx.com");
        String shortUrl = "abcd";
        UrlMappingController controller = new UrlMappingController();
        UrlMappingService urlMappingService = EasyMock.mock(UrlMappingService.class);
        EasyMock.expect(urlMappingService.generateShortUrl(request.getUrl())).andReturn(shortUrl);
        EasyMock.replay(urlMappingService);
        controller.setUrlMappingService(urlMappingService);


        ResponseEntity<String> responseEntity = controller.long2short(request);

        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        Assert.assertTrue(responseEntity.getBody().equals(shortUrl));
    }

    @Test
    public void short2long() {
        String shortUrl = "abcd";
        String longUrl = "http://www.xxx.com";
        UrlMappingController controller = new UrlMappingController();
        UrlMappingService urlMappingService = EasyMock.mock(UrlMappingService.class);
        EasyMock.expect(urlMappingService.querySourceUrl(shortUrl)).andReturn(longUrl);
        EasyMock.replay(urlMappingService);
        controller.setUrlMappingService(urlMappingService);


        ResponseEntity<String> responseEntity = controller.short2long(shortUrl);

        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        Assert.assertTrue(responseEntity.getBody().equals(longUrl));
    }
}