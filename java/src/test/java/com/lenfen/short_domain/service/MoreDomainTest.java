package com.lenfen.short_domain.service;

import com.lenfen.short_domain.ShortDomainApplication;
import com.lenfen.short_domain.api.TransformController;
import com.lenfen.short_domain.api.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 碰撞测试类
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDomainApplication.class)
public class MoreDomainTest {
    @Autowired
    private TransformController transformController;

    @Test
    public void moreDomainTest() {
        int idx = 0;
        while (true) {
            ApiResponse res = transformController.encode(String.valueOf(idx++));
            if (ApiResponse.STATE_OK != res.getCode()) {
                log.info("编码失败,已编码数量:{}", idx);
                return;
            }
            if (idx % 1000000 == 0) {
                log.info("已编码数量：{}", idx);
            }
        }
    }
}
