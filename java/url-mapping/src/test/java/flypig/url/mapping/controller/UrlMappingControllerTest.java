package flypig.url.mapping.controller;

import flypig.url.mapping.DataInitial;
import flypig.url.mapping.bean.GenerateShortUrlRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UrlMappingControllerTest extends DataInitial {

    @Test
    public void long2short() {
        GenerateShortUrlRequest request = new GenerateShortUrlRequest();
        request.setUrl("https://blog.csdn.net/cy");
        UrlMappingController controller = new UrlMappingController();

        ResponseEntity<String> responseEntity = controller.long2short(request);

        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void short2long() {
        String shortUrl = "abcd";
        UrlMappingController controller = new UrlMappingController();

        ResponseEntity<String> responseEntity = controller.short2long(shortUrl);

        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }
}