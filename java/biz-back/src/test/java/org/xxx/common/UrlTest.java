package org.xxx.common;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.xxx.cbfsc.document.constant.Constants;
import org.xxx.controller.UrlController;

import com.alibaba.fastjson.JSONObject;
/**
 * 测试
 * Create by hetao on 2021/11/12 11:33
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=UrlTest.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlTest { 

    private MockMvc mockMvc;
    
    @InjectMocks
    UrlController urlController;
 
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }
    
	@Test
    public  void createShortUrlTest() throws Exception {
		String longUrl = "https://www.cnblogs.com/xifengxiaoma/p/11022146.html";
		String responseString = mockMvc.perform(
				MockMvcRequestBuilders.get("/createShortUrl")    //请求的url,请求的方法是get
                        .accept(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                        .param("longUrl", longUrl))         //添加参数
				.andExpect(MockMvcResultMatchers.status().isOk())    //返回的状态是200
                .andDo(MockMvcResultHandlers.print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
		Constants.shortUrlId = JSONObject.parseObject(responseString).getString("shortUrl")
				.replace("http://", "").replace("https://", "").replace("<br>", "").split("/")[1];
        System.out.println("--------返回的shortUrlId = " + Constants.shortUrlId);
        System.out.println("--------返回的json = " + responseString);
    }
    
	@Test
    public  void getShortUrlTest() throws Exception {
		String responseString = mockMvc.perform(
				MockMvcRequestBuilders.get("/getLongUrl")    //请求的url,请求的方法是get
                        .accept(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                        .param("shortUrlId", Constants.shortUrlId))         //添加参数
				.andExpect(MockMvcResultMatchers.status().isOk())    //返回的状态是200
                .andDo(MockMvcResultHandlers.print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);
    }
}
