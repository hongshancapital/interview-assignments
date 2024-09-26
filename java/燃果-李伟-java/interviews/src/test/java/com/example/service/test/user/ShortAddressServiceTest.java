package com.example.service.test.user;

import com.alibaba.fastjson.JSON;
import com.example.bean.result.ResultRpc;
import com.example.service.ShortAddressService;
import com.example.service.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户业务测试
 * @author eric
 * @date 2021/7/22 22:23
 */
@Slf4j
@EnableAutoConfiguration
@SpringBootTest(classes = ShortAddressServiceTest.class)
public class ShortAddressServiceTest extends BaseTest {

    @Autowired
    private ShortAddressService shortAddressService;

    @Test
    public void genShortAddressTest() {
        ResultRpc resultRpc = shortAddressService.genShortAddress("http://xxx.com/aa/bb/t.html");
        log.info("ResultRpc:{}", JSON.toJSONString((resultRpc)));
        resultRpc = shortAddressService.getLongAddressByShortAddress("5c0oJ");
        log.info("ResultRpc:{}", JSON.toJSONString((resultRpc)));
    }

    @Test
    public void getLongAddressByShortAddressTest() {
        ResultRpc resultRpc = shortAddressService.getLongAddressByShortAddress("5c0oJ");
        log.info("ResultRpc:{}", JSON.toJSONString((resultRpc)));
    }

}
