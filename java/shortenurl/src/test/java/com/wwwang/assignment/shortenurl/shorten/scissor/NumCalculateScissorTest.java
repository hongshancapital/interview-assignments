package com.wwwang.assignment.shortenurl.shorten.scissor;

import com.wwwang.assignment.shortenurl.ShortenUrlApplicationTests;
import com.wwwang.assignment.shortenurl.exception.BizException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicLong;

public class NumCalculateScissorTest extends ShortenUrlApplicationTests {

    @Autowired
    private NumCalculateScissor scissor;

    @Test
    public void test(){
        scissor.num = new AtomicLong(Long.MAX_VALUE-10);
        try {
            scissor.cut("bbbbb");
        } catch (BizException e) {
            Assert.assertEquals("本服务已经无法提供更多短链接，请联系服务提供方",e.getBizMsg());
        }
        scissor.num = new AtomicLong(5000);
    }

    @Test
    public void testToOtherBaseString(){
        String str = NumCalculateScissor.toOtherBaseString(-1,62);
        Assert.assertNotEquals(str,"a");
    }
}
