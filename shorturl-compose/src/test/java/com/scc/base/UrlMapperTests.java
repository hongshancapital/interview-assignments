package com.scc.base;

import com.scc.base.entity.UrlDO;
import com.scc.base.mapper.UrlMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author renyunyi
 * @date 2022/4/26 11:52 PM
 * @description UrlMapper tests
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlComposeApplication.class)
public class UrlMapperTests {

    @Autowired
    public UrlMapper urlMapper;

    public UrlDO urlDO;

    @Before
    public void init(){
        urlDO = new UrlDO();
        urlDO.setId(5008L);
        urlDO.setLongUrl("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx");
        urlDO.setShortUrl("1iM");
    }

    @Test
    public void testGetIdByLongUrl(){
        Long id = urlMapper.getIdByLongUrl(this.urlDO);
        assert id == 5008L;
    }

    @Test
    public void testGetLongUrlById(){
        String longUrl = urlMapper.getLongUrlById(this.urlDO);
        assert StringUtils.equals("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx", longUrl);
    }

    @Test
    public void testAddLongUrl(){
        UrlDO urlDO1 = new UrlDO();
        urlDO1.setLongUrl("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvZZZZ" + new Random().nextInt(10000000));
        long effectLines = urlMapper.addLongUrl(urlDO1);
        assert effectLines == 1;
        assert urlDO1.getId() > 5000;
    }





}
