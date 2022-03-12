package com.tataazy.work.domainmanage.service;

import com.tataazy.work.domainmanage.DomainManageApplicationTests;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

import java.util.ArrayList;


public class UrlProcessServiceTest extends DomainManageApplicationTests {
    public static final Log log = LogFactory.getLog(UrlProcessServiceTest.class);

    @Autowired
    private ShortUrlProcessServiceImpl shortUrlProcessService;

    /* normal case */
    @Test
    public void testGenerateShortUrlFirst() {
        String normalUrl = "https://github.com/scdt-china/interview-assignments";
        String shortUrl = shortUrlProcessService.generateShorUrl(normalUrl);
        if (log.isDebugEnabled()) {
            log.debug("Test First:testGenerateShorUrl,shortUrl : " + shortUrl);
        }
        Assert.assertTrue(!StringUtils.isBlank(shortUrl));

        String resultUrl = shortUrlProcessService.getNormalUrl(shortUrl);
        if (log.isDebugEnabled()) {
            log.debug("Test First:testGenerateShortUrl,resultUrl : " + resultUrl);
        }
        Assert.assertTrue(normalUrl.equalsIgnoreCase(resultUrl));
    }

    @Test
    public void testGenerateShortUrlSecond() {
        String normalUrl = "https://github.com/tataazy";
        String shortUrl = shortUrlProcessService.generateShorUrl(normalUrl);
        if (log.isDebugEnabled()) {
            log.debug("Test Second:testGenerateShorUrl,shortUrl : " + shortUrl);
        }
        Assert.assertTrue(!StringUtils.isBlank(shortUrl));

        String resultUrl = shortUrlProcessService.getNormalUrl(shortUrl);
        if (log.isDebugEnabled()) {
            log.debug("Test Second:testGenerateShortUrl,resultUrl : " + resultUrl);
        }
        Assert.assertTrue(normalUrl.equalsIgnoreCase(resultUrl));
    }

    /* abnormal case */
    @Test
    public void testGenerateShortUrlThird() {
        Assert.assertNull(shortUrlProcessService.generateShorUrl(null));
    }

    @Test
    public void testGenerateShortUrlFourth() {
        Assert.assertNull(shortUrlProcessService.generateShorUrl(""));
    }

    @Test
    public void testGenerateShortUrlFifth() {
        Assert.assertNull(shortUrlProcessService.getNormalUrl(""));
    }

    /* perf test */

    @Test
    public void testShortUrlServicePerfSingle() {
        List<String> shortUrlList = new ArrayList<>(100000);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String normalUrl = "https://github.com/scdt-china/interview-assignments" + i;
            shortUrlList.add(shortUrlProcessService.generateShorUrl(normalUrl));
        }
        //generateShorUrl 100000th need time : 609ms
        log.warn("generateShorUrl 100000th need time : " + (System.currentTimeMillis() - startTime) + "ms");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        long startTime2 = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            shortUrlProcessService.getNormalUrl(shortUrlList.get(j));
        }
        //getNormalUrl 100000th need time : 62ms
        log.warn("getNormalUrl 100000th need time : " + (System.currentTimeMillis() - startTime2) + "ms");
    }

    @Test
    public void testShortUrlServicePerfMulti() {
        for (int i = 0; i < 10; i++) {
            new Thread("" + i) {
                @Override
                public void run() {
                    List<String> shortUrlList = new ArrayList<>(5000);
                    final long startTime = System.currentTimeMillis();
                    while(true) {
                        for (int i = 0; i < 5000; i++) {
                            String normalUrl = "https://github.com/scdt-china/interview-assignments" + i;
                            shortUrlList.add(shortUrlProcessService.generateShorUrl(normalUrl));
                        }
                        break;
                    }
                    log.warn("线程" + getName() + "generateShorUrl 5000th need time : " + (System.currentTimeMillis() - startTime) + "ms");
                }
            }.start();
        }
    }



}
