package com.scdt.shortlink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scdt.shortlink.cache.LinkCache;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.Assert;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ShortlinkTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Value("${cache.maximum}")
    private int CACHE_MAXIMUM;

    @Autowired
    LinkCache linkCache;

    @Test
    @DisplayName("测试错误长链接转换")
    public void testWrongUrl() throws Exception {
        String url = "htt:123";
        MvcResult result = mvc.perform(post("/transform").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("url", url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String code = mapper.readTree(response).get("code").asText();
        Assert.isTrue(!"000000".equals(code), "长链接格式错误");
    }

    @Test
    @DisplayName("测试错误短链接获取")
    public void testWrongKey() throws Exception {
        String[] wrongKeys = new String[]{"abc", "ErrorKey", "ErrorKy_"};
        for (String key : wrongKeys) {
            MvcResult result = mvc.perform(get("/getUrl/".concat(key)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            String response = result.getResponse().getContentAsString();
            String code = mapper.readTree(response).get("code").asText();
            Assert.isTrue(!"000000".equals(code), "错误短链接");
        }
    }


    @Test
    @DisplayName("测试转换长链接后获取")
    public void testTransformAndGetUrl() throws Exception {
        String url = "http://baidu.com";
        String key = transform(url);
        String urlRes = getUrl(key);
        Assert.isTrue(url.equals(urlRes), "链接转换前后不一致");
    }

    @Test
    @DisplayName("测试LRUCache")
    public void test() throws Exception{
        String urlPrefix = "http://baidu.com?rount=";
        String url;

        int totalRound = CACHE_MAXIMUM + 1;
        String[] key = new String[totalRound];
        for (int i = 0; i < totalRound; i++) {
            url = urlPrefix.concat(String.valueOf(i + 1));
            String roundKey = transform(url);
            getUrl(roundKey);
            key[i] = roundKey;
        }

        String urlRound1 = getUrl(key[0]);
        Assert.isTrue("null".equals(urlRound1), "LRUCache失效");
    }

    private String transform(String url) throws Exception{
        MvcResult result = mvc.perform(post("/transform").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("url", url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String key = mapper.readTree(response).get("data").asText();
        Assert.notNull(key, "长链接转换失败");
        return key;
    }

    private String getUrl(String key) throws Exception {
        MvcResult urlResult = mvc.perform(get("/getUrl/".concat(key)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String urlRes = mapper.readTree(urlResult.getResponse().getContentAsString()).get("data").asText();
        return urlRes;
    }
}
