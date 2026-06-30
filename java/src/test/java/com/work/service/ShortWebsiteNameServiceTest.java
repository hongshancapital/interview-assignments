package com.work.service;

import com.work.model.Result;
import com.work.service.ShortWebsiteNameService;
import com.work.util.CacheUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortWebsiteNameServiceTest {
    @Autowired
    private ShortWebsiteNameService shortWebsiteNameService;
    @Autowired
    private CacheUtil cacheUtil;
    private String longName;
    private String shortName;

    @Before
    public void setup() {
        longName = "https://www.baidu.com/s?wd=javauuid%E7%94%9F%E6%88%90%26rsv_spt=1%26rsv_iqid=0xb1b6329800072704%26issp=1%26f=8%26rsv_bp=1%26rsv_idx=2%26ie=utf-8%26rqlang=cn%26tn=baiduhome_pg%26rsv_dl=tb%26rsv_enter=1%26oq=uuid%25E7%2594%259F%25E6%2588%2590%26rsv_btype=t%26inputT=1254%26rsv_t=ba7aO7udRW6Y7rbv2h%2BQbTt51JF9sRn%2Fp94ocIxAgtGIRKJxQQtNvRWEwIoeviVZtA35%26rsv_sug3=26%26rsv_sug1=14%26rsv_sug7=100%26rsv_pq=c03192bc0006fa30%26rsv_sug2=0%26rsv_sug4=2535";

    }


    @Order(1)
    @Test
    public void convertToShortName() {
        Result<String> result = shortWebsiteNameService.convertToShortName(longName);
        Assert.assertNotNull(result.getData());
        shortName = result.getData();
    }


    @Order(2)
    @Test
    public void getLongName() {
        //存在的短域名
        shortName = cacheUtil.getByLongName(longName).getShortWebsiteName();
        Result<String> result = shortWebsiteNameService.getLongName(shortName);
        Assert.assertNotNull(result.getData());

        //不存在的短域名
        shortName = UUID.randomUUID().toString();
        result = shortWebsiteNameService.getLongName(shortName);
        Assert.assertTrue(result.getCode() == HttpStatus.NOT_FOUND.value());
    }
}
