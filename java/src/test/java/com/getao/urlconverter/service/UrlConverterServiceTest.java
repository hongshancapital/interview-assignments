package com.getao.urlconverter.service;

import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.dto.vo.UrlCacheVO;
import com.getao.urlconverter.exception.UrlConvertException;
import com.getao.urlconverter.service.impl.UrlConverterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlConverterServiceTest {

    private static final String longUrl = "www.google.com";
    private static final String emptyUrl = null;

    @Resource
    UrlConverterServiceImpl urlConverterService;

    @Test
    public void testUrlConverterService() {
        GetShortUrlVO vo = urlConverterService.getShortUrl(longUrl);
        String resUrl = vo.getShortUrl();
        assertThat(resUrl).isNotEmpty();
        assertThat(vo.getStatus()).isEqualTo(200);
        GetLongUrlVO vo1 = urlConverterService.getLongUrl(resUrl);
        assertThat(vo1.getLongUrl()).isNotEmpty();
        assertThat(vo1.getLongUrl()).isEqualTo(longUrl);
        assertThat(vo1.getStatus()).isEqualTo(200);
    }

    @Test(expected = UrlConvertException.class)
    public void testUrlConverterServiceBadCase1() {
        GetShortUrlVO vo = urlConverterService.getShortUrl(emptyUrl);
    }

    @Test(expected = UrlConvertException.class)
    public void testUrlConverterServiceBadCase2() {
        GetLongUrlVO vo1 = urlConverterService.getLongUrl(emptyUrl);
    }
}
