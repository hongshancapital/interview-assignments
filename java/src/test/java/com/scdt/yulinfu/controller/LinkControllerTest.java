package com.scdt.yulinfu.controller;

import com.scdt.yulinfu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LinkControllerTest {

    @Autowired
    private LinkController controller;

    @Test
    public void getShortLink() {
        assert null == controller.getShortLink("");
    }

    @Test
    public void getLongLink() {
        assert null == controller.getLongLink("");
    }
}