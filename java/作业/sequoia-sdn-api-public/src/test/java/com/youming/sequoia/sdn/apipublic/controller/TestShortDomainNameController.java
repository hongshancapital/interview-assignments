package com.youming.sequoia.sdn.apipublic.controller;


import com.youming.sequoia.sdn.apipublic.config.SpringApplicationConfig;
import com.youming.sequoia.sdn.apipublic.vo.response.ResponseResultVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringApplicationConfig.class)
public class TestShortDomainNameController {

    @Autowired
    private ShortDomainNameController shortDomainNameController;

    @Test
    public void test() {
        ResponseResultVO<String> responseResultVO = shortDomainNameController.save("http://www.baidu.com");
        responseResultVO = shortDomainNameController.save("http://www.baidu.com");      //重复调用以测试重复分支
        

        shortDomainNameController.get(responseResultVO.getData());
    }
}
