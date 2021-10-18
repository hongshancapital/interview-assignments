package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.Application;
import com.scdt.yulinfu.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DataServiceImplTest {

    @Autowired
    private DataService dataService;

    @Test
    public void getShortLink() {
        // 生成短链接
        assert "http://scdt.cn/00000000"
                .equalsIgnoreCase(dataService.getShortLink("http://test.link.long/current?package=scdt&code=test"));
        assert "http://scdt.cn/10000000"
                .equalsIgnoreCase(dataService.getShortLink("http://test.link.long/current?package=scdt&code=test1"));
        // 异常数据处理
        assert null == dataService.getShortLink(null);
        assert null == dataService.getShortLink("");

        // 重复生成情况
        assert "http://scdt.cn/00000000"
                .equalsIgnoreCase(dataService.getShortLink("http://test.link.long/current?package=scdt&code=test"));
    }

    @Test
    public void getLongLink() {
        assert "http://test.link.long/current?package=scdt&code=test"
                .equalsIgnoreCase(dataService.getLongLink("http://scdt.cn/00000000"));
        assert "http://test.link.long/current?package=scdt&code=test1"
                .equalsIgnoreCase(dataService.getLongLink("http://scdt.cn/10000000"));
        assert null == dataService.getLongLink("");
    }
}