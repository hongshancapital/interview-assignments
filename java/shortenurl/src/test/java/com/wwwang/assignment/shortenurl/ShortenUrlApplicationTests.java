package com.wwwang.assignment.shortenurl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortenUrlApplicationTests {

    @Test
    void test(){
        String[] args = {};
        boolean result = true;
        try {
            ShortenUrlApplication.main(args);
        } catch (Throwable e) {
            result = false;
        }
        Assert.assertEquals(result,true);
    }
}
