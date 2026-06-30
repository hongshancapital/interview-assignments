package city.yyds.job.hsdemo.pref;
import city.yyds.job.hsdemo.service.ShortUrlService;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class PerformanceTest {
    private static final Logger log = LoggerFactory.getLogger(PerformanceTest.class);
    @Autowired
    ShortUrlService shortUrlService;
    int i= 0;
    @JunitPerfConfig(threads = 10, warmUp = 1_000, duration = 60_000
            , reporter = {HtmlReporter.class, ConsoleReporter.class})
    public void getShortUrlTest()throws InterruptedException  {
        String url =new StringBuffer("http://perf.yyds.city/").append(i++).toString();
        String result = shortUrlService.getShortUrl(url);
        Assert.assertNotNull(result);
        Thread.sleep(100);
        log.debug("PerformanceTest num:"+i);
    }

}