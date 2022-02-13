package com.liupf.tiny.url;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liupf.tiny.url.generator.TinyURLGenerator;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class VisitURLControllerTest {

    private String shortURI = "/url/getShort";

    private String longURI = "/url/getLong";

    @Resource
    private MockMvc mvc;

    @Resource
    private TinyURLGenerator tinyURLGenerator;


    @Test
    @DisplayName("访问端链接")
    public void testVisitTinyURL() throws Exception {
        String logUrl = "http://localhost:8081/tiny/swagger-ui.html";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("longUrl", logUrl);
        String shortUrl = sendPost(shortURI, params).get("result").asText();
        Assert.isTrue(StringUtils.isNotBlank(shortUrl), "获取长URL成功");

        String code = tinyURLGenerator.parseCode(shortUrl);
        String visitUrl = "/v/" + code;
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(visitUrl)).andReturn().getResponse();
        Assert.isTrue(Objects.equals(response.getStatus(), 302), "地址重定向");
        Assert.isTrue(Objects.equals(response.getHeader("Location"), logUrl), "重定向地址正确");
    }

    @Test
    @DisplayName("访问端链接-异常分支")
    public void testWrongVisitTinyURL() throws Exception {
        String visitUrl = "/v/12345678";
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(visitUrl)).andReturn().getResponse();
        Assert.isTrue(Objects.equals(response.getStatus(), 404), "地址不存在");
    }

    private JsonNode sendPost(String url, MultiValueMap<String, String> params) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        if (params != null) {
            requestBuilder.params(params);
        }
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readTree(response);
    }

}
