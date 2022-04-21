package interview.assignments;

import interview.assignments.service.IShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhiran.wang
 * @date 2022/4/12 11:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {InterviewAssignmentsApplication.class})
public class TestIShortUrlService {

    @Autowired
    private IShortUrlService shortUrlService;

    @Test
    public void testGetShortUrl(){
        Assert.assertEquals("uMBrU3", shortUrlService.getShortUrl("http://www.baidu.com"));
    }

    @Test
    public void testGetShortUrlNull(){
        Assert.assertNull(shortUrlService.getShortUrl(null));
    }

    @Test
    public void testGetLongUrl(){
        String longUrl = "http://www.baidu.com";
        String shortUrl = shortUrlService.getShortUrl(longUrl);
        Assert.assertEquals(longUrl, shortUrlService.getLongUrl(shortUrl));
    }
}
