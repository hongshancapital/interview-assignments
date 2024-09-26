package com.domainserver;

import com.domainserver.service.IShortDomainService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 短域名服务单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class ShortDomainApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortDomainApplicationTests.class);

    @Autowired
    private IShortDomainService shortDomainService;

    @Test
    void domainTest() {
        String baiduLongDomain = "https://www.baidu.com";
        String weiboLongDomain = "https://passport.weibo.com/";
        String emptyDomain = "";
        String baiduShortDomain = shortDomainService.getShortDomain(baiduLongDomain);
        String weiboShortDomain = shortDomainService.getShortDomain(weiboLongDomain);
        LOGGER.info("baiduShortDomain:{}", baiduShortDomain);
        LOGGER.info("weiboShortDomain:{}", weiboShortDomain);
        LOGGER.info("weiboLongDomain:{}", shortDomainService.getLongDomain(weiboShortDomain));
        LOGGER.info("weiboLongDomain:{}", shortDomainService.getLongDomain(weiboShortDomain));
    }

}
