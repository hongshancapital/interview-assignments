package com.wf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlApplicationTests {

    @Test
    void contextLoads() {
        ShortUrlApplication app = new ShortUrlApplication();


        LinkApiControllerTest la = new LinkApiControllerTest();
        la.toShort();
        la.toLong();
        la.index();

        SwaggerConfigTest sct = new SwaggerConfigTest();
        sct.getUserDocket();
    }
}
