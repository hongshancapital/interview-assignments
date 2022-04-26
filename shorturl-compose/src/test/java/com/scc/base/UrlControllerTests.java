package com.scc.base;

import com.scc.base.entity.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author renyunyi
 * @date  2022/4/24 5:04 PM
 * @description UrlController tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlComposeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlControllerTests {

    @Autowired
    private TestRestTemplate restRestTemplate;

    @LocalServerPort
    private int port;

    private URL UrlObject;

    @Before
    public void setup() throws MalformedURLException {
        String url = String.format("http://127.0.0.1:%d/", port);
        this.UrlObject = new URL(url);
    }

    @Test
    public void testGetShortUrl() {
        JsonResult result = this.restRestTemplate.getForObject(this.UrlObject.toString() + "getShortUrl?longUrl=/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx", JsonResult.class);
        assert StringUtils.equals("1iM", String.valueOf(result.getData()));
    }

    @Test
    public void testGetLongUrl() {
        JsonResult result = this.restRestTemplate.getForObject(this.UrlObject.toString() + "getLongUrl?shortUrl=1iM", JsonResult.class);
        assert StringUtils.equals("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx", String.valueOf(result.getData()));
    }

    @Test
    public void testHello(){
        String result = this.restRestTemplate.getForObject(String.format("http://127.0.0.1:%d/shorturl-compose", port), String.class);
        assert StringUtils.equals("This is shorturl-compose hello page!", result);
    }
}
