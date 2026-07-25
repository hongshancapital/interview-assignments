package com.scdt.aeolus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableCaching
public class ShortUrlControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @Test
    public void getShortUrlTest() throws Exception {
        String testOriginalUrl = "http://www.baidu.com";
        Map<String, String> origialMap = new HashMap<>();
        origialMap.put("OriginalUrl", testOriginalUrl);
        String content = gson.toJson(origialMap);
        String responseString = mockMvc.perform(post("/api/getShortUrl")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
        String shortUrl = jsonObject.get("Response").getAsJsonObject().get("ShortUrl").getAsString();
        Assert.assertTrue(!shortUrl.isEmpty());

        // 直接还原得到的短域名
        Map<String, String> shortMap = new HashMap<>();
        shortMap.put("ShortUrl", shortUrl);
        content = gson.toJson(shortMap);
        responseString = mockMvc.perform(post("/api/getOriginalUrl")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        jsonObject = gson.fromJson(responseString, JsonObject.class);
        String originalUrl = jsonObject.get("Response").getAsJsonObject().get("OriginalUrl").getAsString();
        Assert.assertTrue(!originalUrl.isEmpty());
        Assert.assertEquals(originalUrl, testOriginalUrl);

        // 查询一个不存在的短域名
        Map<String, String> emptyMap = new HashMap<>();
        emptyMap.put("ShortUrl", "http://url.com/xzcdf");
        content = gson.toJson(emptyMap);
        responseString = mockMvc.perform(post("/api/getOriginalUrl")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        jsonObject = gson.fromJson(responseString, JsonObject.class);
        Assert.assertEquals(jsonObject.get("Code").getAsString(), "OriginalUrlNotExist");
    }
}
