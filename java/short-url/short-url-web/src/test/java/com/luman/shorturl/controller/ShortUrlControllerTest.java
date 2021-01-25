package com.luman.shorturl.controller;

import com.luman.shorturl.api.controller.ShortUrlController;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShortUrlControllerTest {

    @Autowired
    ShortUrlController shortUrlController;
    @Value("${domain}")
    private String domain;
    @Test
   public void testFailUrl(){
        ResponseEntity<String> response = shortUrlController.gen("www.baidu.com",null);
        TestCase.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        TestCase.assertEquals(response.getBody(),"url error");


    }
    @Test
    public void testFailExpire(){
        ResponseEntity<String> response =shortUrlController.gen("https://www.baidu.com",-1);
        TestCase.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        TestCase.assertEquals(response.getBody(),"expire error");
        response =shortUrlController.gen("https://www.baidu.com",365*10+1);
        TestCase.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        TestCase.assertEquals(response.getBody(),"expire error");
    }
    @Test
    public void testSuccessGen(){
        String TEST_URL="https://www.baidu.com";
        ResponseEntity<String> response =shortUrlController.gen(TEST_URL,null);
        TestCase.assertEquals(response.getStatusCode(), HttpStatus.OK);
        TestCase.assertNotNull(response.getBody());
        String code = response.getBody().replace(domain,"");
        response = shortUrlController.redirect(code);
        TestCase.assertEquals(response.getStatusCode(),HttpStatus.MOVED_PERMANENTLY);
        List<String> location = response.getHeaders().get("Location");
        TestCase.assertNotNull(location);
        TestCase.assertTrue(location.size()>0);
        TestCase.assertEquals(location.get(0),TEST_URL);
    }
}
