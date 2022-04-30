package com.hongshang.shorturlmodel.impl;

import com.hongshang.common.BaseHolder;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 对urlDao存储类进行测试
 *
 * @author kobe
 * @date 2021/12/19
 */
public class UrlDaoTest {

    @Tested
    UrlDao urlDao;

    @Mocked
    BaseHolder baseHolder;

    @Test
    public void getByKey() {
        urlDao.putKeyValue("123", "23456");
        String result = urlDao.getByKey("123");
        assertEquals("23456",result);
    }

    @Test
    public void getByKey2() {
        String result = urlDao.getByKey("12");
        assertNull(result);
    }

    @Test
    public void removeDelayData() {
        urlDao.putKeyValue("1236", "2345645");
        new Expectations() {
            {
                Deencapsulation.setField(urlDao, "shortUrlTimeout", 30l);
            }
        };
        urlDao.removeDelayData();
    }

    @Test
    public void getShortUrl() {
        new Expectations() {
            {
                Deencapsulation.setField(urlDao, "strStrategy", "snowflakeGetShortStrStrategy");
            }
            {
                BaseHolder.getBean("snowflakeGetShortStrStrategy");
                result = new SnowflakeGetShortStrStrategy();
            }
        };
        String shortUrl = urlDao.getShortUrl();
        assertNotNull(shortUrl);
    }
}