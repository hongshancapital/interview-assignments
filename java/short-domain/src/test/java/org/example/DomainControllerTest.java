package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.response.ApiResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DomainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("长域名转短域名成功")
    public void longToShort_success_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/getShortUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(String.format("{\"url\":\"%s\"}", "http://www.baidu.com"))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
        Assertions.assertTrue(jsonNode.get("errorCode").isNull());
        Assertions.assertTrue(jsonNode.get("message").isNull());
        Assertions.assertTrue(StringUtils.isNotBlank(jsonNode.get("data").asText()));
    }

    @Test
    @DisplayName("长域名转短域名, 无效的url")
    public void longToShort_invalid_url_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/getShortUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(String.format("{\"url\":\"%s\"}", "abcd"))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
        Assertions.assertEquals(ApiResult.INVALID_URL_CODE, jsonNode.get("errorCode").asText());
        Assertions.assertTrue(jsonNode.get("data").isNull());
    }

    @Test
    @DisplayName("短域名转长域名, 无效的url")
    public void shortToLong_invalid_url_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/getLongUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(String.format("{\"url\":\"%s\"}", "abcd"))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
        Assertions.assertEquals(ApiResult.INVALID_URL_CODE, jsonNode.get("errorCode").asText());
        Assertions.assertTrue(jsonNode.get("data").isNull());
    }

    @Test
    @DisplayName("短域名转长域名成功")
    public void shortToLong_success_test() throws Exception {
        String longUrl = "http://www.baidu.com";
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/getShortUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(String.format("{\"url\":\"%s\"}", longUrl))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
        Assertions.assertTrue(jsonNode.get("errorCode").isNull());
        Assertions.assertTrue(jsonNode.get("message").isNull());
        String shortUrl = jsonNode.get("data").asText();
        Assertions.assertTrue(StringUtils.isNotBlank(shortUrl));

        mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/getLongUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(String.format("{\"url\":\"%s\"}", shortUrl))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseContent = mvcResult.getResponse().getContentAsString();
        jsonNode = new ObjectMapper().readTree(responseContent);
        Assertions.assertTrue(jsonNode.get("errorCode").isNull());
        Assertions.assertTrue(jsonNode.get("message").isNull());
        Assertions.assertEquals(longUrl, jsonNode.get("data").asText());
    }
}
