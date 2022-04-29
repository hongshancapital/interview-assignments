package com.sequoiacap.shortlinkcenter.application.business;

import com.sequoiacap.shortlinkcenter.ShortLinkCenterApplicationTests;
import com.sequoiacap.shortlinkcenter.application.business.impl.ShortUrlServiceImpl;
import com.sequoiacap.shortlinkcenter.domain.shorturl.repository.UrlEntityRepository;
import com.sequoiacap.shortlinkcenter.domain.shorturl.task.NotifierTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author h.cn
 * @date 2022/3/17
 */
@SpringBootTest(classes = {ShortUrlServiceImpl.class, NotifierTask.class})
public class ShortUrlServiceTest extends ShortLinkCenterApplicationTests {

    @InjectMocks
    private ShortUrlServiceImpl shortUrlService;

    @Mock
    private UrlEntityRepository urlEntityRepository;

    @Before
    public void before(){
    }

    @Test
    public void getSourceUrlByShortUrl_test(){
        when(urlEntityRepository.getSourceUrlByShortUrl(any())).thenReturn("http://localhost:8888/test");
        String result = shortUrlService.getByShortUrl("1");
        Assert.assertEquals("http://localhost:8888/test", result);
    }

    @Test
    public void getShortUrlBySourceUrl_test_sourceUrl_exist(){
        when(urlEntityRepository.getShortUrlBySourceUrl(ArgumentMatchers.anyString())).thenReturn("http://localhost:8888/test");
        String result = shortUrlService.getBySourceUrl("1","2");
        Assert.assertEquals("http://localhost:8888/test", result);
    }

    @Test
    public void getShortUrlBySourceUrl_test_sourceUrl_not_exist(){
        when(urlEntityRepository.getShortUrlBySourceUrl(ArgumentMatchers.anyString())).thenReturn("");
        doNothing().when(urlEntityRepository).saveShortUrl(any());
        String result = shortUrlService.getBySourceUrl("http://localhost:8888/test","2");
        Assert.assertNotNull(result);
    }
}
