package com.liupf.tiny.url;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TinyURLControllerTest {

    private String shortURI = "/url/getShort";

    private String longURI = "/url/getLong";

    @Resource
    private MockMvc mvc;

    @Test
    @DisplayName("长URL转换-合法请求")
    public void testSaveLongUrl() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("longUrl", "http://localhost:8081/tiny/swagger-ui.html");
        JsonNode jsonNode = sendPost(shortURI, params);
        String code = jsonNode.get("code").asText();
        Assert.isTrue("200".equals(code), "长链接转换成功");
    }


    @Test
    @DisplayName("长URL转换-参数异常")
    public void testWrongSaveLongUrl() throws Exception {
        // URL为空
        String code = sendPost(shortURI, null).get("code").asText();
        Assert.isTrue("400".equals(code), "长链接转换失败-参数为空");

        // URL超长
        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("longUrl", getLongURL());
        String code2 = sendPost(shortURI, params2).get("code").asText();
        Assert.isTrue("10002".equals(code2), "长链接转换失败-URL超长");

        // URL格式错误
        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
        params3.add("longUrl", "http:s//abc");
        String code3 = sendPost(shortURI, params3).get("code").asText();
        Assert.isTrue("10003".equals(code3), "长链接转换失败-URL格式错误");
    }


    @Test
    @DisplayName("获取长URL-合法请求")
    public void testGetLongURL() throws Exception {
        String logUrl = "http://localhost:8081/tiny/swagger-ui.html";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("longUrl", logUrl);
        String shortUrl = sendPost(shortURI, params).get("result").asText();

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("shortUrl", shortUrl);
        JsonNode jsonNode = sendPost(longURI, params2);
        String code = jsonNode.get("code").asText();
        String url = jsonNode.get("result").asText();
        Assert.isTrue("200".equals(code), "获取长URL成功");
        Assert.isTrue(logUrl.equals(url), "获取长URL与原URL相同");
    }


    @Test
    @DisplayName("获取长URL-异常请求")
    public void testWrongGetLongURL() throws Exception {
        String code1 = sendPost(longURI, null).get("code").asText();
        Assert.isTrue("400".equals(code1), "获取长URL失败-参数为空");

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("shortUrl", "http:abc");
        String code2 = sendPost(longURI, params2).get("code").asText();
        Assert.isTrue("10003".equals(code2), "获取长URL失败-URL格式不正确");

        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
        params3.add("shortUrl", "http://localhost:8081/tiny/v/At7ylyjv");
        String code3 = sendPost(longURI, params3).get("code").asText();
        Assert.isTrue("10001".equals(code3), "获取长URL失败-URL失效");

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

    /**
     * 超过2048的URL
     */
    private String getLongURL() {
        return "https://www.google.com/search?q=Mockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA"
                + "oqmockhttpservletrequestbuilder"
                +
                "+paramsgs_lcpCgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wizMockmvc+perform+get+parameter&ei=qXMIYqOYNvHomAXXwqHADA&oq=mockhttpservletrequestbuilder+params&gs_lcp=Cgdnd3Mtd2l6EAEYATIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwA0oECEEYAEoECEYYAFAAWABgxA1oAXABeACAAQCIAQCSAQCYAQDIAQrAAQE&sclient=gws-wiz";
    }
}
