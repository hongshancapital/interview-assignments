package com.example.shorturl.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ShortUrlServiceImpl Tester.
 *
 * @author <mencius>
 * @version 1.0
 * @since <pre>2�� 19, 2021</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShortUrlServiceImplTests {

    @Autowired(required = false)
    private ShortUrlServiceImpl shortUrlServiceImpl;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: queryLongUrl(String code)
     */
    @Test
    public void testQueryLongUrl() throws Exception {

        String fullUrl = shortUrlServiceImpl.queryLongUrl("aaaaaa");

        final String code = shortUrlServiceImpl.genCode("http://www.cctv.com", 10);
        fullUrl = shortUrlServiceImpl.queryLongUrl(code);
    }

    /**
     * Method: genCode(String fullUrl, int expirtTime)
     */
    @Test
    public void testGenCode() throws Exception {
        final String code = shortUrlServiceImpl.genCode("http://www.cctv.com", 10);
    }

    /**
     * Method: shortUrl(String md5)
     */
    @Test
    public void testShortUrl() throws Exception {
        final String md5 = org.springframework.util.DigestUtils.md5DigestAsHex("http://www.cctv.com".getBytes());
        String[] test = shortUrlServiceImpl.shortUrl(md5);
        for (String string : test) {
            System.out.println(string);
        }
    }

}
