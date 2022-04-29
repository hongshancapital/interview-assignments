package interview.shorturl.service;

import interview.shorturl.dao.po.ShortUrlInfo;
import interview.shorturl.exception.BusException;
import interview.shorturl.response.ResponseCodeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * service测试
 *
 * @author: ZOUFANQI
 **/
@SpringBootTest
public class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    void testConvertUrl() {
        // 参数为空
        try {
            this.shortUrlService.convertUrl(null, null);
        } catch (BusException e) {
            assert Objects.equals(e.getCode(), ResponseCodeEnum.PARAM_ERROR.getCode());
        }

        // 失效时间为空
        final ShortUrlInfo info = this.shortUrlService.convertUrl("https://test.com/abc", null);
        assert info != null && info.getExpireOn() == null;

        // 失效时间<0
        final ShortUrlInfo info2 = this.shortUrlService.convertUrl("https://test.com/abc", -1);
        assert info2 != null && info2.getExpireOn() == null;

        // 失效时间正常
        final ShortUrlInfo info3 = this.shortUrlService.convertUrl("https://test.com/abc", 600);
        assert info3 != null && info3.getExpireOn() != null;
    }

    @Test
    void testGet() {
        // 无此数据
        try {
            this.shortUrlService.getInfoByShortUrl("123");
        } catch (BusException e) {
            assert Objects.equals(e.getCode(), ResponseCodeEnum.NOT_FOUND.getCode());
        }

        // 失效数据
        try {
            final ShortUrlInfo info = this.shortUrlService.convertUrl("http://xxxx", 1);
            final String shortUrl = info.getShortUrl();
            TimeUnit.SECONDS.sleep(1);
            this.shortUrlService.getInfoByShortUrl(shortUrl);
        } catch (BusException e) {
            assert Objects.equals(e.getCode(), ResponseCodeEnum.EXPIRED.getCode());
        } catch (Exception e) {
        }

        // 正常数据
        final ShortUrlInfo info = this.shortUrlService.convertUrl("http://xxxx", null);
        assert info != null;

        // 正常数据
        final ShortUrlInfo info2 = this.shortUrlService.convertUrl("http://xxxx", 600);
        assert info2 != null;
    }
}
