package com.shorturl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMappingDaoTest {

    @Autowired
    private UrlMappingDao dao;

    private Long version = 0L;

    @Test
    public void create() throws Exception {
        dao.create(version);
    }

    @Test
    public void insert() throws Exception {
        String url = "https://blog.csdn.net/IT_dog/article/details/87381716";
        Long code = dao.insert(version, url);
        String u = dao.queryUrl(code);

        assertEquals(url, u);
    }

    @Test
    public void delete() throws Exception {
        long code = 1003L;
        dao.delete(code);
    }

    @Test
    public void queryUrl() throws Exception {
        Long code = 3003L;
        String url = dao.queryUrl(code);
        System.out.println(url);
    }

}