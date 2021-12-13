package com.jk.shorturl.dao.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jiang Jikun
 * @Description
 */
@SpringBootTest
class ShortCodeStorageTest {

    @Autowired
    ShortCodeStorage shortCodeStorage;

    @Test
    void getLongURLByshortCode() {
        String longURL = shortCodeStorage.getLongURLByshortCode("abcd");
        System.out.println(longURL);

        longURL = shortCodeStorage.getLongURLByshortCode(null);
        System.out.println(longURL);

    }

    @Test
    void getShortCodeByLongURL() {
        String shortCode = shortCodeStorage.getShortCodeByLongURL("http://www.abce.com.cn/abdddc.html");
        System.out.println(shortCode);

        shortCode = shortCodeStorage.getShortCodeByLongURL("http://www.abce.com.cn/abc.html");
        System.out.println(shortCode);

        shortCode = shortCodeStorage.getShortCodeByLongURL(null);
        System.out.println(shortCode);
    }

    @Test
    void saveShortCode() {
        shortCodeStorage.saveShortCode("abc","http://www.abce.com.cn/abc.html");
        System.out.println(shortCodeStorage.getLongURLByshortCode("abc"));

        shortCodeStorage.saveShortCode(null,null);
        System.out.println(shortCodeStorage.getLongURLByshortCode(null));

        shortCodeStorage.saveShortCode(null,"http://sss.com");
        System.out.println(shortCodeStorage.getLongURLByshortCode(null));
    }
}
