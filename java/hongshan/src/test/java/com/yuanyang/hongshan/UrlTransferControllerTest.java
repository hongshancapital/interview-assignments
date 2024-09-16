package com.yuanyang.hongshan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanyang.hongshan.entity.RequestDTO;
import com.yuanyang.hongshan.entity.Result;
import com.yuanyang.hongshan.entity.ResultCode;
import com.yuanyang.hongshan.util.UrlCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yuanyang
 * @date 2021/12/22 2:36 下午
 * @Describe
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlTransferControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @Autowired
    private UrlCacheUtil urlCacheUtil;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * 模拟http请求
     *
     * @param url
     * @param requestDTO
     * @return
     */
    public Result mockHttpRequest(String url, RequestDTO requestDTO) {
        try {
            MvcResult mvcResult = mockMvc.perform(post(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON.toJSONString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String response = mvcResult.getResponse().getContentAsString();
            Result result = JSONObject.parseObject(response, Result.class);
            return result;
        } catch (Exception e) {
            log.error("UrlTransferControllerTest error", e);
            return null;
        }
    }

    /***
     * 长转短
     * 1、无长链接  2、有长链接 （2.1 有效场链接  2.2 无效长链接）
     */

    @Test
    public void testGetShortUrlDateNull() {
        RequestDTO requestDTO = new RequestDTO();
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        Assert.assertEquals("数据异常",ResultCode.ILLEGAL_PARAM, result.getRc());
    }

    @Test
    public void testGetShortUrlNoLongUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setShortUrl("1111111");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        Assert.assertEquals("数据异常",ResultCode.ILLEGAL_PARAM, result.getRc());
    }

    @Test
    public void getShortUrlValidLongUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        System.out.println(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void getShortUrlNoValidLongUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        Assert.assertEquals("参数异常", ResultCode.ILLEGAL_PARAM, result.getRc());
    }

    /***
     * 短转长
     * 1、无短链接  2、有短链接  （2.1 短链接有效 2.2 短链接无效 2.3 短链接有效，长时间没用LRU干掉了）
     */
    @Test
    public void testGetLongUrlNoDate() {
        RequestDTO requestDTO = new RequestDTO();
        Result result = mockHttpRequest("/url/getLongUrl", requestDTO);
        Assert.assertEquals("参数异常", ResultCode.ILLEGAL_PARAM, result.getRc());
    }


    @Test
    public void testGetLongUrlNoShortUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/getLongUrl", requestDTO);
        Assert.assertEquals("参数异常", ResultCode.ILLEGAL_PARAM, result.getRc());
    }

    @Test
    public void testGetLongUrlNoValidShortUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        RequestDTO shortRes = new RequestDTO();
        shortRes.setShortUrl("hongshan");
        Result res = mockHttpRequest("/url/getLongUrl", shortRes);
        Assert.assertEquals("数据不存在或者异常", ResultCode.ILLEGAL_DATA, res.getRc());

    }

    @Test
    public void testGetLongUrlValidShortUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        RequestDTO shortRes = new RequestDTO();
        shortRes.setShortUrl(result.getValue().toString());
        Result res = mockHttpRequest("/url/getLongUrl", shortRes);
        System.out.println(res.getValue());
        Assert.assertEquals("数据存在","https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin", String.valueOf(res.getValue()));
    }

    @Test
    public void testExpireShortUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Result result = mockHttpRequest("/url/generateShortURL", requestDTO);
        String shortUrl = String.valueOf(result.getValue());
        urlCacheUtil.remove(shortUrl);
        RequestDTO shortRes = new RequestDTO();
        shortRes.setShortUrl(shortUrl);
        Result res = mockHttpRequest("/url/getLongUrl", shortRes);
        System.out.println(res.getValue());
        Assert.assertEquals("短链接失效", ResultCode.ILLEGAL_DATA_NO_VALID, res.getRc());
    }

}