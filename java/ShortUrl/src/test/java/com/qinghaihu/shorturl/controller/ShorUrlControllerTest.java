package com.qinghaihu.shorturl.controller;

import com.alibaba.fastjson.JSON;
import com.qinghaihu.shorturl.entity.ResolveUrlInfo;
import com.qinghaihu.shorturl.entity.SaveUrlInfo;
import com.qinghaihu.shorturl.service.ShortUrlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ShorUrlControllerTest {

    @InjectMocks
    @Autowired
    private ShorUrlController shorUrlController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShortUrlService shortUrlService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 长链接正常转换为短链接
     * @throws Exception
     */
    @Test
    public void getAndSaveShortUrl() throws Exception {
        String mockShortUrlValue = "rtH47D";
        doReturn(mockShortUrlValue).when(shortUrlService).transferToShortUrl(any());

        SaveUrlInfo saveUrlInfo = new SaveUrlInfo();
        saveUrlInfo.setLongUrl("%HelloSequoiaCapital*&");
        String reqJson = JSON.toJSONString(saveUrlInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/getAndSave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(mockShortUrlValue));


    }

    /**
     * 测试待转换长链接为空值，Service抛出异常，返回转换失败和失败异常信息
     * @throws Exception
     */
    @Test
    public void longUrlIsEmpty() throws Exception {
        RuntimeException exception = new RuntimeException("待转换长链接为空值");
        doThrow(exception).when(shortUrlService).transferToShortUrl(isNull());

        SaveUrlInfo saveUrlInfo = new SaveUrlInfo();
        String reqJson = JSON.toJSONString(saveUrlInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/getAndSave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value(exception.getMessage()));

    }


    /**
     * 测试shortUrl不存在场景
     */
    @Test
    public void resolveShortUrlNotExist() throws Exception {
        doThrow(new RuntimeException("该段连接不存在")).when(shortUrlService).transferToLongUrl(any());

        ResolveUrlInfo resolveUrlInfo = new ResolveUrlInfo();
        resolveUrlInfo.setShortUrl("HelloWorld");
        String reqJson = JSON.toJSONString(resolveUrlInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/resolveShortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }


    /**
     * 测试shortUrl正常解析场景
     */
    @Test
    public void resolveShortUrl() throws Exception {
        String shortUrl = "jhQaC";
        String mockLongUrlValue = "%HelloSequoiaCapital*&";
        doReturn(mockLongUrlValue).when(shortUrlService).transferToLongUrl(shortUrl);

        ResolveUrlInfo resolveUrlInfo = new ResolveUrlInfo();
        resolveUrlInfo.setShortUrl(shortUrl);
        String solveReqJson = JSON.toJSONString(resolveUrlInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/resolveShortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(solveReqJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(mockLongUrlValue));

    }
}