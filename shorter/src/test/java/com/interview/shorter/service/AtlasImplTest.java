package com.interview.shorter.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
@SpringBootTest
public class AtlasImplTest {
    ShorterImMemImpl.AtlasImpl atlas;

    Shorter shorter = new ShorterImMemImpl();

    @Test
    public void testCreate() {
        String source = "http://www.baidu.com";
        String shortCode = shorter.shorting(0, source);

        atlas = new ShorterImMemImpl.AtlasImpl(shortCode, source);

        Assert.assertNotEquals(0, atlas.getKey());
        Assert.assertNotEquals(0, atlas.getAux());
        Assert.assertNotEquals(0, atlas.getAux1());
        Assert.assertNotNull(atlas.getCreateAt());
        Assert.assertEquals(atlas.getContent(), source);

        Assert.assertEquals(atlas.getType(), 0);
        Assert.assertNotNull(atlas.reset());


    }
}
