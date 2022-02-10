package com.example.shortlongurl.web;

import com.example.shortlongurl.ShortLongUrlApplication;
import com.example.shortlongurl.framework.result.R;
import com.example.shortlongurl.web.model.ShortLongUrlModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortLongUrlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlGenControllerTest {
    @Autowired
    private UrlGenController urlGenController;

    @Test
    public void getShortUrl() {
        R<ShortLongUrlModel> r = urlGenController.getShortUrl("https://github.com/scdt-china/interview-assignments");
        System.out.println(r);
    }

    @Test
    public void getLongUrl() {
       R<ShortLongUrlModel> r1 = urlGenController.getShortUrl("https://github.com/scdt-china/interview-assignments");
       R<ShortLongUrlModel> r2 = urlGenController.getLongUrl(r1.getData().getShortUrl());
        System.out.println(r1);
        System.out.println(r2);
    }
}