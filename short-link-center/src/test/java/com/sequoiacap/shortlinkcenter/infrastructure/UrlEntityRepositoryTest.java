package com.sequoiacap.shortlinkcenter.infrastructure;

import com.sequoiacap.shortlinkcenter.ShortLinkCenterApplicationTests;
import com.sequoiacap.shortlinkcenter.domain.shorturl.entity.UrlEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author h.cn
 * @date 2022/3/17
 */
@SpringBootTest(classes = {UrlEntityRepositoryImpl.class})
public class UrlEntityRepositoryTest extends ShortLinkCenterApplicationTests {

    @InjectMocks
    private UrlEntityRepositoryImpl urlEntityRepository;

    @Before
    public void before(){
    }

    @Test
    public void saveShortUrl_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setSourceUrl("http://localhost:8888/test");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
    }

    @Test(expected = NullPointerException.class)
    public void saveShortUrl_exception_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
    }

    @Test
    public void getShortUrlBySourceUrl_exist_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setSourceUrl("http://localhost:8888/test");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
        String targetUrl = urlEntityRepository.getShortUrlBySourceUrl("http://localhost:8888/test");
        Assert.assertEquals("http://h.cn/QWETYU", targetUrl);
    }

    @Test
    public void getShortUrlBySourceUrl_not_exist_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setSourceUrl("http://localhost:8888/test");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
        String targetUrl = urlEntityRepository.getShortUrlBySourceUrl("http://localhost:8888/test1");
        Assert.assertNull(targetUrl);
    }

    @Test
    public void getSourceUrlByShortUrl_exist_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setSourceUrl("http://localhost:8888/test");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
        String targetUrl = urlEntityRepository.getSourceUrlByShortUrl("http://h.cn/QWETYU");
        Assert.assertEquals("http://localhost:8888/test", targetUrl);
    }

    @Test
    public void getSourceUrlByShortUrl_not_exist_test(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setProtocol("http");
        urlEntity.setSourceUrl("http://localhost:8888/test");
        urlEntity.setTargetUrl("http://h.cn/QWETYU");
        urlEntityRepository.saveShortUrl(urlEntity);
        String targetUrl = urlEntityRepository.getSourceUrlByShortUrl("http://localhost:8888/test1");
        Assert.assertNull(targetUrl);
    }
}
