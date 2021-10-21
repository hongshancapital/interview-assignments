package ligq.url;

import ligq.url.exception.RepeatUrlException;
import ligq.url.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static ligq.url.MainApplicationTests.testUrl;

/**
 * 单元测试类
 * @author ligq
 * @since 2021-10-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test2 {

    @Resource
    private UrlService service;


    @Test(expected = RepeatUrlException.class)
    public void getRepeatShortUrlTest() throws Exception {
        service.getShortUrl(testUrl);
    }
}
