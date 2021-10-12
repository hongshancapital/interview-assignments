package com.jk.shorturl.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jiang Jikun
 * @Description
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ConfigMainUtilTest {

    @Autowired
    ConfigMainUtil configMainUtil;

    @Test
    void setDomain() {
        configMainUtil.setDomain("http://a.cn");
        System.out.println(configMainUtil.getDomain());
    }

    @Test
    void getCodeFromURL() {
        String code = configMainUtil.getCodeFromURL("http://a.cn");
        System.out.println(code);

        code = configMainUtil.getCodeFromURL("http://a.cn/");
        System.out.println(code);

        code = configMainUtil.getCodeFromURL("http://a.cn/abc");
        System.out.println(code);
    }
}
