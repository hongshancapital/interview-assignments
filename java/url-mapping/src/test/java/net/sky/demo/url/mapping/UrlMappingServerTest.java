package net.sky.demo.url.mapping;

import net.sky.demo.url.mapping.bean.GenerateShortUrlRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlMappingServerTest {


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void long2short() {
        String url = "http://www.xxx.com";
        GenerateShortUrlRequest request = new GenerateShortUrlRequest();
        request.setUrl(url);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/url_mapping/long2short", request, String.class);
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        Assert.assertTrue(StringUtils.isNotBlank(responseEntity.getBody()));
    }

    @Test
    public void short2long() {
        ResponseEntity<String> short2longResponseEntity = null;
        String shortUrl = "1ABC";
        short2longResponseEntity = restTemplate.getForEntity("/url_mapping/short2long/" + shortUrl, String.class);
        Assert.assertTrue(short2longResponseEntity.getStatusCode() == HttpStatus.OK);
        Assert.assertNull(short2longResponseEntity.getBody());
        try {
            //insert one
            String url = "http://www.xxx.com";
            GenerateShortUrlRequest request = new GenerateShortUrlRequest();
            request.setUrl(url);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("/url_mapping/long2short", request, String.class);
            shortUrl = responseEntity.getBody();

            short2longResponseEntity = restTemplate.getForEntity("/url_mapping/short2long/" + shortUrl, String.class);
            Assert.assertTrue(short2longResponseEntity.getStatusCode() == HttpStatus.OK);
            Assert.assertTrue(short2longResponseEntity.getBody().equals(url));

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}