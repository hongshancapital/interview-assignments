package demo;

import com.alibaba.fastjson.JSONObject;
import demo.common.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TinyUrlDemoApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void testTransUrl() throws Exception {
        RequestBuilder request;

        String oriURL = "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        request = post("/api/url/ori/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"oriURL\":\"%s\"}", oriURL));
        MvcResult mvcResult1 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);

        String curURL = result1.getData();
        request = post("/api/url/cur/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"curURL\":\"%s\"}", curURL));
        MvcResult mvcResult2 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);

        assertEquals(oriURL, result2.getData());
    }

    @Test
    public void testTransUrlLoop() throws Exception {
        RequestBuilder request;

        String[] oris = new String[]{
                "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate1",
                "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate2",
                "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate3",
                "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate4",
                "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate5"
        };
        String[] curs = new String[5];

        for (int i = 0; i < 5; i++) {
            String oriURL = oris[i];
            request = post("/api/url/ori/v1/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"oriURL\":\"%s\"}", oriURL));
            MvcResult mvcResult1 = mvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);

            String curURL = result1.getData();
            request = post("/api/url/cur/v1/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"curURL\":\"%s\"}", curURL));
            MvcResult mvcResult2 = mvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);

            curs[i] = result2.getData();
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(oris[i], curs[i]);
        }
    }

    @Test
    public void testWrongUrl() throws Exception {
        RequestBuilder request;

        String oriURL = "htps://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        request = post("/api/url/ori/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"oriURL\":\"%s\"}", oriURL));
        MvcResult mvcResult1 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);

        assertEquals(200, result1.getCode());
        assertNull(result1.getData());


        String curURL = "https:/t.cn/miH";
        request = post("/api/url/cur/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"curURL\":\"%s\"}", curURL));
        MvcResult mvcResult2 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);

        assertEquals(200, result2.getCode());
        assertNull(result2.getData());
    }

    @Test
    public void testWrongPrefixCurUrl() throws Exception {
        RequestBuilder request;

        String curURL = "https://x.cn/miHf8n1K9";
        request = post("/api/url/cur/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"curURL\":\"%s\"}", curURL));
        MvcResult mvcResult2 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);

        assertEquals(200, result2.getCode());
        assertNull(result2.getData());
    }

    @Test
    public void testLongCurUrl() throws Exception {
        RequestBuilder request;

        String curURL = "https://t.cn/miHf8n1K9";
        request = post("/api/url/cur/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.format("{\"curURL\":\"%s\"}", curURL));
        MvcResult mvcResult2 = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);

        assertEquals(200, result2.getCode());
        assertNull(result2.getData());
    }
}
