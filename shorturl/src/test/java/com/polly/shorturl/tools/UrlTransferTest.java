package com.polly.shorturl.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.24 15:58:05
 */
@SpringBootTest
public class UrlTransferTest {
    @Autowired
    private UrlTransfer transfer;

    @Test
    public void test01() {
        transfer.convert("test");
    }
}
