package com.wg;

import com.wg.common.Result;
import com.wg.controller.DomainController;
import com.wg.vo.DomainVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainControllerTest {

    @Autowired
    private DomainController domainController;

    @Test
    public void getShortUrl(){
        DomainVO domainVO = new DomainVO();
        domainVO.setUrl("http://www.02468.xyz");
        Result<String> shortUrl = domainController.getShortUrl(domainVO);
        Assert.assertEquals("短连接", "https://test:9999/4689658c", shortUrl.getData());
    }

    @Test
    public void getRealUrl(){
        DomainVO domainVO = new DomainVO();
        domainVO.setUrl("https://test:9999/4689658c");
        Result<String> realUrl = domainController.getRealUrl(domainVO);
        Assert.assertEquals("长链接", "http://www.02468.xyz", realUrl.getData());
    }

}
