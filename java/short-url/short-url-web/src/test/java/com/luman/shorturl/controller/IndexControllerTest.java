package com.luman.shorturl.controller;

import com.luman.shorturl.api.controller.IndexController;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IndexControllerTest {
    @Autowired
    IndexController indexController;
    @Test
    public void testIndex(){
        TestCase.assertEquals(indexController.index(),"index");
    }
}
