package com.hs.shortlink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.shortlink.domain.constant.ResultStatusEnum;
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
    private static final String DATA_ERROR = "error";
    private static final String LONG_URL_FIELD = "longUrl";
    private static final String SHORT_URL_FIELD = "shortUrl";
    private static final String LENGTH_FIELD = "length";
    private static final String GET_SHORT_URL = "/urlTransform/getShortUrl";
    private static final String GET_LONG_URL = "/urlTransform/getLongUrl";
    //测试长链url
    private static final String TEST_LONG_URL1 = "http://www.baidu.com";
    private static final String TEST_LONG_URL2 = "http://www.douyu.com";

    @Test
    void basicTest() throws Exception {
        final Map<String, String> shortUrlResponse = getShortUrlResponse(TEST_LONG_URL1, 6);
        final Map longUrlResonse = getLongUrlResonse(shortUrlResponse.get(DATA_FIELD));
        //校验是否转换正确
        Assertions.assertEquals(TEST_LONG_URL1, longUrlResonse.get(DATA_FIELD));
    }

    @Test
    void testCoverage1() throws Exception {
        final Map<String, String> shortUrlResponse = getShortUrlResponse(TEST_LONG_URL2, 2);
        Assertions.assertEquals(ResultStatusEnum.PARAM_INVALID.getError(), shortUrlResponse.get(DATA_ERROR));
    }

    @Test
    void testCoverage2() throws Exception {
        final Map<String, String> shortUrlResponse = getShortUrlResponse("", 2);
        Assertions.assertEquals(ResultStatusEnum.PARAM_INVALID.getError(), shortUrlResponse.get(DATA_ERROR));
    }

    private Map<String, String> getShortUrlResponse(String longUrl, Integer length) throws Exception {
        //根据长链获取短链
        final String shortUrlResponse = mockMvc.perform(
                MockMvcRequestBuilders.get(GET_SHORT_URL)
                        .param(LONG_URL_FIELD, longUrl)
                        .param(LENGTH_FIELD, String.valueOf(length)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        //短链转化为长链
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(shortUrlResponse, Map.class);
    }

    private Map getLongUrlResonse(String shortUrl) throws Exception {
        final String longUrlResponse = mockMvc.perform(
                MockMvcRequestBuilders.get(GET_LONG_URL)
                        .param(SHORT_URL_FIELD, shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(longUrlResponse, Map.class);
    }

}
