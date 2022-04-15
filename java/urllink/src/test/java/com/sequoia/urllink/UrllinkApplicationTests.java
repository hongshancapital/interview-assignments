package com.sequoia.urllink;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.sequoia.urllink.urllink.dao")
class UrllinkApplicationTests {

    @Test
    void contextLoads() {
    }

}
