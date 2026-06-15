package com.scdt.shortenurl.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Author chenlipeng
 * @Date 2022/3/6 9:52 下午
 */
public class EncodeUtilsTest {

    @Test
    public void encodeByMd5() throws NoSuchAlgorithmException {
        String encode = EncodeUtils.encodeByMd5("www.baidu.com", "chenlipeng");
        Assert.assertEquals("4F2A3370099936123FB6D7C68AAE3DD3", encode);
    }

    @Test
    public void encodeBy62Decimal() {
        String value = EncodeUtils.encodeBy62Decimal(6200L);
        Assert.assertEquals("1C0", value);
    }

}
