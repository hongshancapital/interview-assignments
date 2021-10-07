package com.liukun.shortdomain.service;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.liukun.shortdomain.utils.UrlExchange;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/7       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/7
 * </p>
 */
@SpringBootTest
public class DomainServiceTest {
    private UrlExchange urlExchange = new UrlExchange();

//    @Test
    @JunitPerfConfig(threads = 2,warmUp = 1000, duration = 2000,reporter = {HtmlReporter.class})
    public void createShortUrl() {
        String longUrl = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
        urlExchange.long2Short(longUrl);
    }

    @Test
    public void getLongUrl() {
        String longUrl = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
        String shortUrl = urlExchange.long2Short(longUrl);
        Assert.assertEquals(longUrl, urlExchange.short2Long(shortUrl));
    }
}