package com.wwwang.assignment.shortenurl.controller;

import com.wwwang.assignment.shortenurl.ShortenUrlApplicationTests;
import com.wwwang.assignment.shortenurl.entity.request.GetLongUrlReq;
import com.wwwang.assignment.shortenurl.entity.request.GetShortUrlReq;
import com.wwwang.assignment.shortenurl.entity.response.BizBaseResponse;
import com.wwwang.assignment.shortenurl.entity.response.BizEnum;
import com.wwwang.assignment.shortenurl.entity.response.GetLongUrlResp;
import com.wwwang.assignment.shortenurl.entity.response.GetShortUrlResp;
import com.wwwang.assignment.shortenurl.exception.BizException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ShortenUrlControllerTest extends ShortenUrlApplicationTests {

    @Autowired
    private ShortenUrlController controller;
    @Test
    void testLoop() {
        CompletionService<String> completionService = new ExecutorCompletionService(Executors.newFixedThreadPool(8));
        int times = 100000;
        IntStream.range(0,times).forEach(a->{
            completionService.submit(()->{
                String longUrl = "http://"+UUID.randomUUID().toString().replace("-","")+".baidu.com";
                GetShortUrlReq shortReq = new GetShortUrlReq();
                shortReq.setUrl(longUrl);
                String shortUrl = controller.getShortUrl(shortReq).getData().getShortUrl();
                System.out.println(Thread.currentThread().getName()+"--生成的短链接是："+shortUrl);
                GetLongUrlReq longReq = new GetLongUrlReq();
                longReq.setShortUrl(shortUrl);
                String finalUrl = controller.getLongUrl(longReq).getData().getUrl();
                Assert.assertEquals(longUrl, finalUrl);
                return shortUrl+"--"+longUrl;
            });
        });

        for(int i=1;i<=times;i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void test() {
        String longUrl = "http://baidu.com";
        GetShortUrlReq shortReq = new GetShortUrlReq();
        shortReq.setUrl(longUrl);
        String shortUrl = controller.getShortUrl(shortReq).getData().getShortUrl();

        GetLongUrlReq longReq = new GetLongUrlReq();
        longReq.setShortUrl(shortUrl);
        String finalUrl = controller.getLongUrl(longReq).getData().getUrl();
        Assert.assertEquals(longUrl,finalUrl);

        String longUrl2 = "http://baidu2.com";
        GetShortUrlReq shortReq2 = new GetShortUrlReq();
        shortReq2.setUrl(longUrl2);
        String shortUrl2 = controller.getShortUrl(shortReq2).getData().getShortUrl();

        GetLongUrlReq longReq2 = new GetLongUrlReq();
        longReq2.setShortUrl(shortUrl2);
        String finalUrl2 = controller.getLongUrl(longReq2).getData().getUrl();
        Assert.assertEquals(longUrl2,finalUrl2);
    }



    @Test
    void testError() {
        GetLongUrlReq longReq = new GetLongUrlReq();
        longReq.setShortUrl("999999A");
        BizBaseResponse<GetLongUrlResp> response = controller.getLongUrl(longReq);
        Assert.assertEquals(response.getCode(), BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(), "无效短链接");
    }

    @Test
    void testError2() {
        GetLongUrlReq longReq = new GetLongUrlReq();
        longReq.setShortUrl("jsfioi@#j232323");
        BizBaseResponse<GetLongUrlResp> response = controller.getLongUrl(longReq);
        Assert.assertEquals(response.getCode(), BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(), "无效短链接");
    }

    @Test
    void testError3() {
        GetLongUrlReq longReq = new GetLongUrlReq();
        longReq.setShortUrl("");
        BizBaseResponse<GetLongUrlResp> response = controller.getLongUrl(longReq);
        Assert.assertEquals(response.getCode(), BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(), "非法请求参数");
    }

    @Test
    void testError4() {
        GetShortUrlReq shortReq = new GetShortUrlReq();
        shortReq.setUrl("");
        BizBaseResponse<GetShortUrlResp> response = controller.getShortUrl(shortReq);
        Assert.assertEquals(response.getCode(), BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(), "非法请求参数");
    }

    @Test
    void testException(){
        BizBaseResponse response = ShortenUrlController.processException(new BizException("test"));
        Assert.assertEquals(response.getCode(),BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(),"test");

        response = ShortenUrlController.processException(new Exception());
        Assert.assertEquals(response.getCode(),BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(),"系统错误，操作失败");
    }

}
