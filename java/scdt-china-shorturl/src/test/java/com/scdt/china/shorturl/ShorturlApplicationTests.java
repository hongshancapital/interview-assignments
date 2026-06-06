package com.scdt.china.shorturl;

import com.scdt.china.shorturl.common.GlobalErrorCode;
import com.scdt.china.shorturl.common.ResultVo;
import com.scdt.china.shorturl.controller.UrlController;
import com.scdt.china.shorturl.pojo.Url;
import com.scdt.china.shorturl.pojo.request.LongUrlRequest;
import com.scdt.china.shorturl.pojo.request.ShortUrlRequest;
import com.scdt.china.shorturl.pojo.response.LongUrlResponse;
import com.scdt.china.shorturl.pojo.response.ShortUrlResponse;
import com.scdt.china.shorturl.service.UrlService;
import com.scdt.china.shorturl.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ShorturlApplicationTests {

    @Autowired
    private UrlController urlController;

    /**
     * 测试获取短链接口
     */
    @Test
    public void getShortUrl() {
        // 先保存一个
        LongUrlRequest longUrlRequest = new LongUrlRequest();
        longUrlRequest.setLongUrl("http://localhost:8080/api/url/abc");
        ResultVo<LongUrlResponse> saveShortUrl = urlController.saveShortUrl(longUrlRequest);
        String shortUrl = saveShortUrl.getData().getShortUrl();

        ShortUrlRequest shortUrlRequest = new ShortUrlRequest();
        shortUrlRequest.setShortUrl(shortUrl);
        ResultVo<ShortUrlResponse> resultVo = urlController.getLongUrl(shortUrlRequest);

        Assert.assertEquals(GlobalErrorCode.SUCCESS.getCode(),(resultVo.getCode()));
    }

    /**
     * 请求一个没有建立映射关系的的连接
     */
    @Test
    public void getShortUrlError1(){
        ShortUrlRequest shortUrlRequest = new ShortUrlRequest();
        shortUrlRequest.setShortUrl("http://www.baidu.com");
        ResultVo<ShortUrlResponse> resultVo = urlController.getLongUrl(shortUrlRequest);

        Assert.assertEquals(GlobalErrorCode.NO_SHORT_URL.getCode(),(resultVo.getCode()));
    }


    /**
     * 测试保存短链接口
     */
    @Test
    public void saveShortUrl() {
        LongUrlRequest longUrlRequest = new LongUrlRequest();
        longUrlRequest.setLongUrl("http://localhost:8080/api/url/123456");
        ResultVo<LongUrlResponse> resultVo = urlController.saveShortUrl(longUrlRequest);
        log.info("正确的请求路径" + JacksonUtil.toJson(resultVo));
        Assert.assertEquals(GlobalErrorCode.SUCCESS.getCode(),(resultVo.getCode()));
    }

    /**
     * 保存相同的短链接
     */
    @Test
    public void saveShortUrlError1() {
        LongUrlRequest longUrlRequest = new LongUrlRequest();
        longUrlRequest.setLongUrl("http://localhost:8080/api/url/123456");
        urlController.saveShortUrl(longUrlRequest);
        ResultVo<LongUrlResponse> resultVo = urlController.saveShortUrl(longUrlRequest);

        Assert.assertEquals(GlobalErrorCode.SHORTURL_CREATE_FAIL.getCode(), resultVo.getCode());
    }



}
