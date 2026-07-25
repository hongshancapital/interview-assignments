package com.jk.shorturl.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jiang Jikun
 * @Description
 */
@SpringBootTest
@WebAppConfiguration
class ShortServiceImplTest {

    @Resource
    ShortService shortService;


    @Test
    void generalShortURL() {
        String longURL = "https://www.uuu.com/ssss.html";
        String shortUrl = shortService.generalShortURL(longURL);
        System.out.println(shortUrl);

        longURL = "https://www.uuu.com/bbbb.html";
        shortUrl = shortService.generalShortURL(longURL);
        System.out.println(shortUrl);

        longURL = "https://www.uuu.com/ssss.html";
        shortUrl = shortService.generalShortURL(longURL);
        System.out.println(shortUrl);

    }

    @Test
    void getLongURL() {
        String shortUrl = "https://t.com/abc";
        String longURL = shortService.getLongURL(shortUrl);
        System.out.println(longURL);

        shortUrl = "https://www.uuu.com/w";
        longURL = shortService.getLongURL(longURL);
        System.out.println(longURL);



    }
}
