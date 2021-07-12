package cn.office.luxixi.urlservice.controller;

import cn.hutool.core.lang.Assert;
import cn.office.luxixi.urlservice.UrlServiceApplication;
import cn.office.luxixi.urlservice.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UrlServiceApplication.class})
public class TestUrlController {

    @Autowired
    private UrlController urlController;


    @Test
    public void testUrl() {
        Result<String> result = urlController.getShort("https://www.b.com/add/ll?a=0&b=99&iu=kdkdkdkdkdkdk&id=999");
        Assert.notNull(result.getData());
        log.debug("shortUrl:{}", result.getData());
        Result<String> r = urlController.getLong(result.getData());
        Assert.notNull(r.getData());
        log.debug("longUrl:{}", r.getData());
    }

}
