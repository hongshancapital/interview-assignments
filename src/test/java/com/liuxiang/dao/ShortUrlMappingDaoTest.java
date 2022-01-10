package com.liuxiang.dao;

import com.BaseTest;
import com.liuxiang.model.po.ShortUrlMappingPo;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author liuxiang6
 * @date 2022-01-07
 **/
public class ShortUrlMappingDaoTest extends BaseTest {

    @Resource
    private ShortUrlMappingDao shortUrlMappingDao;

    @Test
    public void testinsert(){
        ShortUrlMappingPo shortUrlMappingPo = new ShortUrlMappingPo();
        shortUrlMappingPo.setShortUrl("test_aaa");
        shortUrlMappingPo.setLongUrl("test_bbb");
        shortUrlMappingPo.setCreateTime(System.currentTimeMillis());

        shortUrlMappingDao.insert(shortUrlMappingPo);

        ShortUrlMappingPo test_aaa = shortUrlMappingDao.getByShortUrl("test_aaa");
        Assert.assertEquals(test_aaa.getLongUrl(), "test_bbb");
    }
}