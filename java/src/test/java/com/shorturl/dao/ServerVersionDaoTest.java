package com.shorturl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerVersionDaoTest {

    @Autowired
    private ServerVersionDao dao;

    @Test
    public void insert() throws Exception {
        System.out.println("Initial Code: " + dao.insert());
    }
}