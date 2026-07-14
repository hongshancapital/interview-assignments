package com.scdt.sdn;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.scdt.sdn.controller.SdnController;
import com.scdt.sdn.dto.ResponseDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdnApplication.class)
public class SdnControllerTest {

    @Autowired
    private SdnController sdnController;

    @Test
    public void encodeTest() {
        ResponseDto<String> sdnRst = sdnController.encode("https://www.sequoiacap.cn/china/companies/wanda-group/");
        Assert.assertTrue("长度超过8位", sdnRst.getData().length() <= 8);
    }

    @Test
    public void encodeAndDecodeTest() {
        String url = "https://www.sequoiacap.cn/china/companies/wanda-group/";
        ResponseDto<String> sdnRst = sdnController.encode(url);
        Assert.assertTrue("长度超过8位", sdnRst.getData().length() <= 8);
        ResponseDto<String> decodeRst = sdnController.decode(sdnRst.getData());
        Assert.assertEquals("加解密错误", url, decodeRst.getData());
    }

    @Test
    public void notFoundTest() {
        String sdnRst = "aaaaaaaa";
        try {
            ResponseDto<String> decodeRst = sdnController.decode(sdnRst);
        } catch (Exception e) {
        }
    }

}
