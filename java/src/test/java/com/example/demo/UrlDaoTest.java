/**
 * @(#)UrlDaoTest.java, 12月 27, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo;

import com.example.demo.application.UrlDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhengyin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlDaoTest {

    @Autowired
    private UrlDao urlDao;

    @Test
    public void testSaveAndGet() {
        String key = "ket";
        String value = "value";
        urlDao.save(key, value);
        String value1 = urlDao.queryLongUrl(key);
        Assert.assertEquals(value, value1);
    }

    @Test
    public void testRepeatSaveAndGet() {
        String key = "ket";
        String value = "value";
        urlDao.save(key, "value1");
        urlDao.save(key, value);
        String value1 = urlDao.queryLongUrl(key);
        Assert.assertEquals(value, value1);
    }
}