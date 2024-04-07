package com.wangxiao.shortlink.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangxiao.shortlink.ShortlinkApplication;
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

@SpringBootTest(classes = ShortlinkApplication.class)
@AutoConfigureMockMvc
public class ShortLinkResourceMockTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    void webTest() throws Exception {
        String longLink = "https://www.baidu.com";
        final String encodeStr = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortlink/encode")
                    .param("longLink", longLink)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> encodeMap = objectMapper.readValue(encodeStr, Map.class);

        final String decodeStr = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortlink/decode")
                    .param("shortLink", encodeMap.get("data"))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> decodeMap = objectMapper.readValue(decodeStr, Map.class);

        Assertions.assertEquals(longLink, decodeMap.get("data"));

    }
}
