package com.yuanyang.hongshan;

import com.yuanyang.hongshan.entity.RequestDTO;
import com.yuanyang.hongshan.entity.Result;
import com.yuanyang.hongshan.entity.ResultCode;
import com.yuanyang.hongshan.service.UrlService;
import com.yuanyang.hongshan.util.UrlCacheUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author yuanyang
 * @date 2021/12/21 10:02 下午
 * @Describe
 * 转短链接服务： 1、正常的长链接转化  2、不正常的长链接转换（格式不对，参数没有）
 * 转长链接服务： 1、正确的短链接   2、不存在的短链接  3、存在的长链接正常返回   4、存在的短链接过期了
 *
 *
 *
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlServiceImplTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlCacheUtil urlCacheUtil;

    @Test
    public void testGenerateShortURL() {
        try {
            Result<String> result = urlService.generateShortURL(buildLongDTO());
            System.out.println("result: " + result);
            Assert.assertTrue(result.isSuccess());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testNoExistShortUrl() {
        RequestDTO requestDTO1 = new RequestDTO();
        requestDTO1.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result<String> shortUrl1 = urlService.generateShortURL(requestDTO1);
        Result<String> test2 = urlService.getLongUrlByShortUrl(buildShortDTO("test"));
        Assert.assertEquals("数据异常或不存在", ResultCode.ILLEGAL_DATA, test2.getRc());
    }


    @Test
    public void testGetLongUrl() {
        try {
            Result<String> shortRes = urlService.generateShortURL(buildLongDTO());
            String shortUrl = shortRes.getValue();
            Result<String> result = urlService.getLongUrlByShortUrl(buildShortDTO(shortUrl));
            System.out.println("result: " + result);
            Assert.assertTrue(result.isSuccess());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLongUrlByExpireShort() {
        Result<String> shortRes = urlService.generateShortURL(buildLongDTO());
        String shortUrl = shortRes.getValue();
        urlCacheUtil.remove(shortUrl);
        Result<String> result = urlService.getLongUrlByShortUrl(buildShortDTO(shortUrl));
        Assert.assertEquals("短链接已失效，请重试", ResultCode.ILLEGAL_DATA_NO_VALID, result.getRc());
    }

    @Test
    public void testNoValidLongUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result<String> res = urlService.generateShortURL(requestDTO);
        Assert.assertEquals("数据异常或不存在", ResultCode.ILLEGAL_PARAM, res.getRc());
    }

    private RequestDTO buildLongDTO() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        return requestDTO;
    }

    private RequestDTO buildShortDTO(String shortUrl) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setShortUrl(shortUrl);
        return requestDTO;
    }

}