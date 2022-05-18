package com.hs.shortlink;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@SpringBootTest(classes = ShortLinkApplication.class)
@AutoConfigureMockMvc
class ShortLinkApplicationTest {

    @Resource
    private MockMvc mockMvc;

    private static final String DATA_FIELD = "data";
    private static final String LONG_URL_FIELD = "longUrl";
    private static final String SHORT_URL_FIELD = "shortUrl";
    private static final String LENGTH_FIELD = "length";
    private static final String GET_SHORT_URL = "/urlTransform/getShortUrl";
    private static final String GET_LONG_URL = "/urlTransform/getLongUrl";
    //测试长链url
    private static final String TEST_LONG_URL = "http://www.baidu.com";

    @Test
    void getShortUrlTest() throws Exception {
        //根据长链获取短链
        final String shortUrlResponse = mockMvc.perform(
                MockMvcRequestBuilders.get(GET_SHORT_URL)
                        .param(LONG_URL_FIELD, TEST_LONG_URL)
                        .param(LENGTH_FIELD, String.valueOf(6)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        //短链转化为长链
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> shortUrlResult = objectMapper.readValue(shortUrlResponse, Map.class);

        final String longUrlResponse = mockMvc.perform(
                MockMvcRequestBuilders.get(GET_LONG_URL)
                        .param(SHORT_URL_FIELD, shortUrlResult.get(DATA_FIELD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> longUrlResult = objectMapper.readValue(longUrlResponse, Map.class);

        //校验是否转换正确
        Assertions.assertEquals(TEST_LONG_URL, longUrlResult.get(DATA_FIELD));
    }

}
