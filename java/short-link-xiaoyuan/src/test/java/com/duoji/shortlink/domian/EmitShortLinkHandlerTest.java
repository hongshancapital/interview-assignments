package com.duoji.shortlink.domian;

import com.duoji.shortlink.domain.impl.EmitShortLinkHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 21:49:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmitShortLinkHandlerTest {
    @Resource
    private EmitShortLinkHandler emitShortLinkHandler;

    @Test(expected = IllegalArgumentException.class)
    public void testEmitShortLinkHandler(){
        String shortLink = emitShortLinkHandler.generateShortLink("http://baidu.tc/wewe1211wqwq.html");
        Assert.assertNotNull(shortLink);
        Assert.assertEquals("http://baidu.tc/wewe1211wqwq.html",emitShortLinkHandler.findLongLink(shortLink));

        Assert.assertNull(emitShortLinkHandler.findLongLink("1213weadq"));
        emitShortLinkHandler.generateShortLink(null);
    }
}
