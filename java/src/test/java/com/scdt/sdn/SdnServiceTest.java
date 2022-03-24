package com.scdt.sdn;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.scdt.sdn.service.SdnService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdnApplication.class)
public class SdnServiceTest {

    @Autowired
    private SdnService sdnService;

    @Test
    public void encodeTest() {
        String sdnRst = sdnService.encode("https://www.sequoiacap.cn/china/companies/wanda-group/");
        Assert.assertTrue("长度超过8位", sdnRst.length() <= 8);
    }

    @Test
    public void encodeAndDecodeTest() {
        String url = "https://www.sequoiacap.cn/china/companies/wanda-group/";
        String sdnRst = sdnService.encode(url);
        Assert.assertTrue("长度超过8位", sdnRst.length() <= 8);
        String decodeRst = sdnService.decode(sdnRst);
        Assert.assertEquals("加解密错误", url, decodeRst);
    }

    @Test
    public void notFoundTest() {
        String sdnRst = "aaaaaaaa";
        try {
            String decodeRst = sdnService.decode(sdnRst);
        } catch (Exception e) {
        }
    }

}
