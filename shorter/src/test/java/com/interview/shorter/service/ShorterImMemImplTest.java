package com.interview.shorter.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
@SpringBootTest
public class ShorterImMemImplTest{
    Shorter shorter = new ShorterImMemImpl();

    /**
     * test params
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal(){
        shorter.shorting(0,null);

    }

    /**
     * test same source
     */
    @Test
    public void testShort(){
        String source = "http://www.google.com";
       String shortCode = shorter.shorting(0,source);
       String shortCode2 = shorter.shorting(0,source);
        Assert.assertEquals(shortCode,shortCode2);
    }
    @Test
    public void testExist(){
        String source = "http://www.google.com";
        String shortCode = shorter.shorting(0,source);
        Assert.assertTrue(shorter.hasExist(shortCode));
    }

    @Test
    public void testRestore() {
        String source = "http://www.google.com";
        String shortCode = shorter.shorting(0, source);
        Shorter.Atlas atlas = shorter.restore(shortCode);
        Assert.assertEquals(atlas.getContent(), source);
    }

    @Test
    public void testUpdate() {
        String source = "http://www.google.com";
        String shortCode = shorter.shorting(0, source);
        shorter.update(shortCode, "http://www.baidu.com");
        Shorter.Atlas atlas = shorter.restore(shortCode);
        Assert.assertEquals(atlas.getContent(), "http://www.baidu.com");
    }

    @Test
    public void testReplace() {
        String source = "www.google.com";
        String shortCode = shorter.shorting(0, source);
        Shorter.Body body = shorter.replace(shortCode);
        Assert.assertEquals(body.getContent(), "http://" + source);
    }

    @Test
    public void testSetLength() {
        ShorterImMemImpl shorterImMem = (ShorterImMemImpl) shorter;
        shorterImMem.setLength(11);
        Assert.assertEquals(shorterImMem.getLength(), 11);

    }

    @Test
    public void testSetRadix() {
        ShorterImMemImpl shorterImMem = (ShorterImMemImpl) shorter;
        shorterImMem.setRadix(25);
        Assert.assertEquals(shorterImMem.getRadix(), 25);

    }

}
