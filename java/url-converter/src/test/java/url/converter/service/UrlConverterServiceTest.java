package url.converter.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import url.converter.facade.vo.LongUrlGetReq;
import url.converter.facade.vo.LongUrlGetRes;
import url.converter.facade.vo.ShortUrlAddReq;
import url.converter.facade.vo.ShortUrlAddRes;
import url.converter.service.impl.UrlConverterServiceImpl;

/**
 * @author Wang Siqi
 * @date 2022/1/1
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class UrlConverterServiceTest {

    @Mock
    private CheckService checkService;

    @InjectMocks
    private UrlConverterServiceImpl urlConverterService;

    @Test
    public void testAddShortUrl() {
        checkService.checkLongUrl(Mockito.anyString());

        ShortUrlAddReq req = ShortUrlAddReq.builder()
                .longUrl("https://segmentfault.com/a/11900000411467ba")
                .build();
        ShortUrlAddRes res = ShortUrlAddRes.builder()
                .shortUrl("https://segmentfault.com/10")
                .build();

//        Mockito.when(urlConverterService.addShortUrl(req)).thenReturn(res);
        ShortUrlAddRes result = urlConverterService.addShortUrl(req);
        Assert.assertEquals(res, result);
    }

    @Test
    public void testAddShortUrl2() {
        checkService.checkLongUrl(Mockito.anyString());

        ShortUrlAddReq req = ShortUrlAddReq.builder()
                .longUrl("http://www.aabbcc.com/adfsalkdgji")
                .build();
        /*ShortUrlAddRes res = ShortUrlAddRes.builder()
                .shortUrl("https://segmentfault.com/10")
                .build();*/

        try {
            ShortUrlAddRes result = urlConverterService.addShortUrl(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddShortUrl3() {
        checkService.checkLongUrl(Mockito.anyString());

        ShortUrlAddReq req = ShortUrlAddReq.builder()
                .longUrl("http://www.aabbcc.com/adfsalkdgji/testmax")
                .build();
        /*ShortUrlAddRes res = ShortUrlAddRes.builder()
                .shortUrl("https://segmentfault.com/10")
                .build();*/

        try {
            ShortUrlAddRes result = urlConverterService.addShortUrl(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetLongUrl() {
        checkService.checkLongUrl(Mockito.anyString());

        LongUrlGetReq req = LongUrlGetReq.builder()
                .shortUrl("https://segmentfault.com/10")
                .build();
        LongUrlGetRes res = LongUrlGetRes.builder()
                .longUrl("https://segmentfault.com/a/11900000411467ba")
                .build();

        LongUrlGetRes result = urlConverterService.getLongUrl(req);
        Assert.assertEquals(res, result);
    }

    @Test
    public void testGetLongUrl2() {
        checkService.checkLongUrl(Mockito.anyString());

        LongUrlGetReq req = LongUrlGetReq.builder()
                .shortUrl("https://segmentfault.com/10000")
                .build();

        try {
            LongUrlGetRes result = urlConverterService.getLongUrl(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
