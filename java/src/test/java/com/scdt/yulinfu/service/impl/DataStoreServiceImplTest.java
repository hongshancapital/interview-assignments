package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.Application;
import com.scdt.yulinfu.service.DataStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataStoreServiceImplTest {

    @Autowired
    private DataStoreService dataStoreService;

    @Test
    public void saveData() {
        dataStoreService.saveData(null, null);
        dataStoreService.saveData("shortLink", null);
        dataStoreService.saveData(null, "longLink");
        dataStoreService.saveData("shortLink", "longLink");
    }

    @Test
    public void getLongLink() {
        assert null == dataStoreService.getLongLink(null);
        assert null == dataStoreService.getLongLink("");
        assert "longLink"
                .equalsIgnoreCase(dataStoreService.getLongLink("shortLink"));
    }

    @Test
    public void getShortLink() {
        assert null == dataStoreService.getShortLink(null);
        assert null == dataStoreService.getShortLink("");
        assert "shortLink"
                .equalsIgnoreCase(dataStoreService.getShortLink("longLink"));
    }
}