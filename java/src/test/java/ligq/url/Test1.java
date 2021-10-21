package ligq.url;

import cn.hutool.core.util.StrUtil;
import ligq.url.service.UrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static ligq.url.MainApplicationTests.shortUrl;
import static ligq.url.MainApplicationTests.testUrl;

/**
 * 单元测试类
 * @author ligq
 * @since 2021-10-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Resource
    private UrlService service;

    @Test
    public void getShortUrlTest() throws Exception {
        shortUrl = service.getShortUrl(testUrl);
        Assert.assertTrue(StrUtil.isNotEmpty(shortUrl));
    }

    /*@Test(expected = RepeatUrlException.class)
    public void getRepeatShortUrlTest() throws Exception {
        service.getShortUrl(testUrl);
    }

    @Test
    public void getExistOriginUrlTest() throws Exception {
        String originUrl = service.getOriginUrl(shortUrl);
        Assert.assertTrue(StrUtil.equals(originUrl, testUrl));
    }

    @Test
    public void getNotExistOriginUrlTest() throws Exception {
        String originUrl = service.getOriginUrl("abcdef");
        Assert.assertTrue(StrUtil.isEmpty(originUrl));
    }*/
}
