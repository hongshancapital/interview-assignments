package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CacheTest {

    private String generateShortUrl = "/service/shortUrlFacade/generateShortUrl";

    private String getOriginalUrl = "/service/shortUrlFacade/getOriginalUrl";

    @Autowired
    private MockMvc mockMvc;


    /**
     * 验证缓存
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        System.out.println("缓存大小为3");

        //
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(generateShortUrl)
                .param("url", "www.baidu.com")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        System.out.println("www.baidu.com, 短链接:" + mvcResult.getResponse().getContentAsString());

        //
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(generateShortUrl)
                .param("url", "www.sohu.com")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        System.out.println("www.sohu.com, 短链接:" + mvcResult.getResponse().getContentAsString());

        //
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(generateShortUrl)
                .param("url", "www.qq.com")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        System.out.println("www.qq.com, 短链接:" + mvcResult.getResponse().getContentAsString());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(generateShortUrl)
                .param("url", "www.163.com")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //
        System.out.println("www.163.com, 短链接:" + mvcResult.getResponse().getContentAsString());

        //
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getOriginalUrl)
                .param("shortUrl", "http://t.cn/YUVCRqc7")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        System.out.println("http://t.cn/YUVCRqc7短链接得到源链接:" + mvcResult.getResponse().getContentAsString());

        //
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getOriginalUrl)
                .param("shortUrl", "http://t.cn/Zh3imgYx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        System.out.println("http://t.cn/Zh3imgYx短链接得到源链接:" + mvcResult.getResponse().getContentAsString());
    }
}
