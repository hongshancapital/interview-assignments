package com.hongshang.shorturlmodel.po;

import mockit.Tested;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 对AddrUrlPo类进行测试
 *
 * @author kobe
 * @date 2021/12/19
 */
public class AddrUrlPoTest {

    @Tested
    AddrUrlPo addrUrlPo;

    @Test
    public void getUrlStr() {
        addrUrlPo.setUrlStr("123");
        assertEquals("123",addrUrlPo.getUrlStr());
    }

    @Test
    public void getStartDate() {
        Date date = new Date();
        addrUrlPo.setStartDate(date);
        assertEquals(date,addrUrlPo.getStartDate());
    }

    @Test
    public void setUrlStr() {
        addrUrlPo.setUrlStr("1235");
    }

    @Test
    public void setStartDate() {
        Date date = new Date();
        addrUrlPo.setStartDate(date);
    }

    @Test
    public void testEquals() {
        assertTrue( addrUrlPo.equals(new AddrUrlPo()));
    }

    @Test
    public void testEquals2() {
        new AddrUrlPo().setUrlStr("123");
        assertFalse( addrUrlPo.equals(null));
    }

    @Test
    public void canEqual() {
        assertTrue(addrUrlPo.canEqual(new AddrUrlPo()));
    }

    @Test
    public void testHashCode() {
        assertNotNull(addrUrlPo.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(addrUrlPo.toString());
    }
}