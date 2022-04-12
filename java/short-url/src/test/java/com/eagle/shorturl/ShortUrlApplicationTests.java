package com.eagle.shorturl;

import com.eagle.shorturl.controller.ShortUrlController;
import com.eagle.shorturl.param.LongUrlParam;
import com.eagle.shorturl.result.Result;
import com.eagle.shorturl.service.ShortUrlService;
import com.eagle.shorturl.util.AuthUtil;
import com.eagle.shorturl.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlApplicationTests {

    @Resource
    private ShortUrlController shortUrlController;

    @Resource
    private ShortUrlService shortUrlService;

    @Resource
    private MockMvc mockMvc;

    @Test
    void add() {
        String longUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        LongUrlParam param = new LongUrlParam(longUrl);
        Result<String> addResult = shortUrlController.add(param);
        assertNotNull(addResult);
        assertNotNull(addResult.getData());
        log.info("test add method, longUrl:{}, shortUrl:{}", longUrl, addResult.getData());
    }

    @Test
    void get() {
        String shortUrl = "1yzC4R";
        Result<String> getResult = shortUrlController.get(shortUrl);
        assertNotNull(getResult);
        assertNotNull(getResult.getData());
        log.info("test get method, shortUrl:{}, longUrl:{}", shortUrl, getResult.getData());
    }

    @Test
    void addAndGet() {
        String longUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        LongUrlParam param = new LongUrlParam(longUrl);
        Result<String> addResult = shortUrlController.add(param);
        assertNotNull(addResult);
        assertNotNull(addResult.getData());
        String shortUrl = addResult.getData();
        Result<String> getResult = shortUrlController.get(shortUrl);
        assertNotNull(getResult);
        assertNotNull(getResult.getData());
        assertEquals(longUrl, getResult.getData());
    }

    @Test
    void authFailed() throws Exception {
        String longUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        LongUrlParam param = new LongUrlParam(longUrl);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/shortUrl/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.getJsonString(param));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());;
    }

    @Test
    void authSucceed() throws Exception {
        String longUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        LongUrlParam param = new LongUrlParam(longUrl);
        long ts = System.currentTimeMillis();
        String sign = AuthUtil.generate(String.valueOf(ts));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/shortUrl/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.getJsonString(param))
                .header("ts",ts)
                .header("sign",sign);
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void getParamFailed() throws Exception {
        long ts = System.currentTimeMillis();
        String sign = AuthUtil.generate(String.valueOf(ts));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/shortUrl/get")
                .header("ts",ts)
                .header("sign",sign);
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void postParamFailed1() throws Exception {
        long ts = System.currentTimeMillis();
        String sign = AuthUtil.generate(String.valueOf(ts));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/shortUrl/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("ts",ts)
                .header("sign",sign);
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void postParamFailed2() throws Exception {
        long ts = System.currentTimeMillis();
        String sign = AuthUtil.generate(String.valueOf(ts));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/shortUrl/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.getJsonString(new LongUrlParam()))
                .header("ts",ts)
                .header("sign",sign);
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void postParamFailed3() throws Exception {
        long ts = System.currentTimeMillis();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/shortUrl/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.getJsonString(new LongUrlParam()))
                .header("ts",ts)
                .header("sign","errorSign");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void addInService() {
        String longUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        String shortUrl = shortUrlService.add(longUrl);
        assertNotNull(shortUrl);
        log.info("test add method, longUrl:{}, shortUrl:{}", longUrl, shortUrl);
    }

}
