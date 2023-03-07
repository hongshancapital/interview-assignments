package com.scdt.shortenurl.storage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Description
 * @Author chenlipeng
 * @Date 2022/3/1 4:24 下午
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShortenUrlStorageTest {

    @Resource(name = "shortenUrlCache")
    private ShortenUrlStorage shortenUrlStorage;

    @Test
    public void testCache() {
        String url = "www.baidu.com";
        shortenUrlStorage.save("qMnMze", url);
        String value = shortenUrlStorage.get("qMnMze");
        Assert.assertEquals(url, value);
    }
}
