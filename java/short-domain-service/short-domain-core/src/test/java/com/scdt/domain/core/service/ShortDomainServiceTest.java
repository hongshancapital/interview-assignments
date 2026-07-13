package com.scdt.domain.core.service;

import com.alibaba.fastjson.JSON;
import com.scdt.domain.api.ShortDomainService;
import com.scdt.domain.api.common.Response;
import com.scdt.domain.core.ApplicationMain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author tuxiaozhou
 * @date 2022/4/2
 */
@Slf4j
@WebAppConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationMain.class})
public class ShortDomainServiceTest {

    private MockMvc mockMvc;
    @Resource
    private ShortDomainService shortDomainService;
    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateShortUrl() {
        Response<String> response = shortDomainService.createShortUrl("https://www.baidu.com");
        assertSuccess(response);
        System.out.println(JSON.toJSONString(response));

        Response<String> response2 = shortDomainService.createShortUrl("https://www.baidu.com");
        assertSuccess(response2);
        System.out.println(JSON.toJSONString(response2));
    }

    @Test
    public void testCreateShortUrl2() {
        Response<String> response = shortDomainService.createShortUrl("");
        Assert.assertNotEquals(Response.SUCCESS, response.getCode());
    }

    @Test
    public void testCreateShortUrl3() {
        Response<String> response = shortDomainService.createShortUrl("www.baidu.com");
        Assert.assertNotEquals(Response.SUCCESS, response.getCode());
    }

//    @Test
//    public void testCreateShortUrl4() {
//        for (int i = 0; i < 10000; i++) {
//            shortDomainService.createShortUrl("https://www.baidu " + i + ".com");
//        }
//        Response<String> response = shortDomainService.createShortUrl("https://www.baidu.com");
//        Assert.assertNotEquals(Response.SUCCESS, response.getCode());
//    }

    @Test
    public void testGetLongUrl() {
        Response<String> response = shortDomainService.createShortUrl("https://www.baidu.com");
        assertSuccess(response);
        Response<String> response2 = shortDomainService.getLongUrl(response.getData());
        assertSuccess(response2);
        System.out.println(JSON.toJSONString(response2));
    }

    @Test
    public void testGetLongUrl2() {
        Response<String> response = shortDomainService.getLongUrl("http://localhost/api/long-url/ZgvvvvvvA");
        Assert.assertNotEquals(Response.SUCCESS, response.getCode());
    }

    @Test
    public void testGetLongUrl3() {
        Response<String> response = shortDomainService.getLongUrl("");
        Assert.assertNotEquals(Response.SUCCESS, response.getCode());
    }

    @Test
    public void testMvc() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8080/api/short-url")
                .param("longUrl", "https://www.baidu.com")
        ).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        System.out.println(mvcResult.getResponse().getContentAsString());

        Response<?> response = JSON.parseObject(mvcResult.getResponse().getContentAsString(), Response.class);
        String shortUrl = (String) response.getData();
        String shortUri = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/long-url/" + shortUri)
        ).andReturn();
        System.out.println(mvcResult2.getResponse().getContentAsString());
        Assert.assertEquals(200, mvcResult2.getResponse().getStatus());
    }

    private <T> void assertSuccess(Response<T> response) {
        Assert.assertNotNull("response is null", response);
        Assert.assertEquals("response code is not 200", Response.SUCCESS, response.getCode());
    }

}
