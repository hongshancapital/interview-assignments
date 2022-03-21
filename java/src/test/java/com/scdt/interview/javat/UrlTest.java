package com.scdt.interview.javat;

import com.scdt.interview.javat.controller.UrlConverterController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlTest {

    @Autowired
    private UrlConverterController urlConverterController;

    @Test
    @DisplayName("normal case: Test if true from long url to short url， single thread ")
    public void normalToSUrlTest() {
        assertThat(urlConverterController).isNotNull();

        // 长地址转短地址
        String lUrl = "https://www.baidu.com";
        String sUrl = urlConverterController.toSUrl(lUrl);
        assertNotNull(sUrl);

        String sameSUrl = urlConverterController.toSUrl(lUrl);
        assertEquals(sUrl, sameSUrl);

        // 短地址转长地址
        assertEquals(lUrl, urlConverterController.toLUrl(sUrl));
    }

    @Test
    @DisplayName("normal case: Test if true from long url to short url，multi thread")
    public void normalMutipleThreadToSUrlTest() {
        assertThat(urlConverterController).isNotNull();

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(30);

        for (Integer i = 0; i < 10000000; i++) {
            executor.submit(() -> {
                // 长地址转短地址
                String lUrl = url();
                String sUrl = urlConverterController.toSUrl(lUrl);
                assertNotNull(sUrl);

                // 短地址转长地址
                assertEquals(lUrl, urlConverterController.toLUrl(sUrl));

                return null;
            });
        }
    }

    @Test
    @DisplayName("exception case")
    public void badCaseToSUrlTest() {
        assertThat(urlConverterController).isNotNull();

        // 非法lurl
        String[] lUrls = {null, "abc"};
        for (String lUrl : lUrls) {
            assertInvalidLUrl(lUrl);
        }

        // 非法surl
        String[] sUrls = {null, "abc", "dB", "ab1B", "ab1Bab1B", "ab1Bab1Bab1B", "~ab1B"};
        for (String sUrl : sUrls) {
            assertInvalidSUrl(sUrl);
        }

    }

    private void assertInvalidLUrl(String lUrl) {
        String sUrl = urlConverterController.toSUrl(lUrl);
        assertNull(sUrl);
    }

    private void assertInvalidSUrl(String lUrl) {
        String sUrl = urlConverterController.toLUrl(lUrl);
        assertNull(sUrl);
    }

    private String url() {
        StringBuffer sb = new StringBuffer();
        sb.append("https://");
        String uuid = UUID.randomUUID().toString();
        sb.append(uuid);
        sb.append(".com");
        return sb.toString();
    }


}
