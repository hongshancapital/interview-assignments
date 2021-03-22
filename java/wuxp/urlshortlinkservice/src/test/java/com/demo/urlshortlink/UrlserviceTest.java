package com.demo.urlshortlink;

import com.demo.urlshortlink.dto.UrlLongRequest;
import com.demo.urlshortlink.repository.UrlLinkRepository;
import com.demo.urlshortlink.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class UrlserviceTest {


    @Mock
    UrlLinkRepository urlLinkRepository;

    @InjectMocks
    UrlService urlService;

    /*
       测试url转换service
     */
    @Test
    public void convertToShortUrlTest() {
        UrlLongRequest url = new UrlLongRequest();
        url.setLongUrl("https://www.google.com/abc");
        url.setExpiresDate(new Date());
        String str= urlService.transferNumbertoStr(100000000001L);
        assertEquals(100000000001L, urlService.fromShortUrltoNumber(str));
    }
}
