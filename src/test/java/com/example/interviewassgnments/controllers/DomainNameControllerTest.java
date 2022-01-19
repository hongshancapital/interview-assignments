package com.example.interviewassgnments.controllers;

import com.alibaba.fastjson.JSONObject;
import com.example.interviewassgnments.InterviewAssgnmentsApplication;
import com.example.interviewassgnments.utils.IConstantUtils;
import com.example.interviewassgnments.utils.ResultEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DomainNameController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/19/2022</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InterviewAssgnmentsApplication.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class DomainNameControllerTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }



    /**
     * Method: toShortLink(@RequestParam(name = "fullURL") String longDomainLink)
     */
    @Test
    public void testToShortLink() throws Exception {
        Assert.assertTrue(execTestToShortLinkException("www.baidu.com",ResultEnum.SUCCESS));
    }

    /**
     * Method: toShortLink(@RequestParam(name = "fullURL") String longDomainLink)
     */
    @Test
    public void testToShortLink_Empty() throws Exception {
        Assert.assertTrue(execTestToShortLinkException("",ResultEnum.PARAM_IS_BLANK));
    }

    /**
     * Method: toShortLink(@RequestParam(name = "fullURL") String longDomainLink)
     */
    @Test
    public void testToShortLink_Matches() throws Exception {
        Assert.assertTrue(execTestToShortLinkException("www.中国",ResultEnum.EXCEPTION_ILLEGAL_CHARACTER));
    }

    /**
     * Method: getFullLink(@RequestParam(name = "shortLink") String shortLink)
     */
    @Test
    public void testGetFullLink() throws Exception {
        String fullLink = "www.baidu.com";
        RequestBuilder request = MockMvcRequestBuilders.post(IConstantUtils.URL_DOMAIN_TOSHORTLINK)
                .param("fullURL",fullLink);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())  //返回的状态是200
                .andDo(print())              //打印出请求和相应的内容
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();//将相应的数据转换为字符串
        String shortLinkCode = JSONObject.parseObject(content).getJSONObject("data").getString("shortLink");

        request = MockMvcRequestBuilders.get(IConstantUtils.URL_DOMAIN_GETFULLLINK)
                .param("shortLink",shortLinkCode);
        mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())  //返回的状态是200
                .andDo(print())              //打印出请求和相应的内容
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();//将相应的数据转换为字符串
        System.out.println(content);
        String result = JSONObject.parseObject(content).getJSONObject("data").getString("fullLink");
        Assert.assertEquals(result,fullLink);
    }

    /**
     * Method: getFullLink(@RequestParam(name = "shortLink") String shortLink)
     */
    @Test
    public void testGetFullLink_Empty() throws Exception {
        Assert.assertTrue(execTestGetFullLinkException("",ResultEnum.PARAM_IS_BLANK));
    }

    /**
     * Method: getFullLink(@RequestParam(name = "shortLink") String shortLink)
     */
    @Test
    public void testGetFullLink_Exception() throws Exception {
        Assert.assertTrue(execTestGetFullLinkException("t.a/999999",ResultEnum.PARAM_IS_INVALID));
    }

    private boolean execTestGetFullLinkException(String shortLink,ResultEnum resultEnum) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(IConstantUtils.URL_DOMAIN_GETFULLLINK)
                .param("shortLink",shortLink);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())  //返回的状态是200
                .andDo(print())              //打印出请求和相应的内容
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();//将相应的数据转换为字符串
        Integer code = JSONObject.parseObject(content).getInteger("code");
        String message = JSONObject.parseObject(content).getString("message");
        return resultEnum.getCode().equals(code) && resultEnum.getMessage().equals(message);
    }

    private boolean execTestToShortLinkException(String longLink,ResultEnum resultEnum) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post(IConstantUtils.URL_DOMAIN_TOSHORTLINK)
                .param("fullURL",longLink);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())  //返回的状态是200
                .andDo(print())              //打印出请求和相应的内容
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();//将相应的数据转换为字符串
        System.out.println(content);
        Integer code = JSONObject.parseObject(content).getInteger("code");
        String message = JSONObject.parseObject(content).getString("message");
        return resultEnum.getCode().equals(code) && resultEnum.getMessage().equals(message);
    }


} 
