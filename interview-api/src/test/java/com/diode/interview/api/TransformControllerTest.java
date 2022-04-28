package com.diode.interview.api;

import com.diode.interview.api.models.request.TransformLongRequest;
import com.diode.interview.api.models.request.TransformShortRequest;
import com.diode.interview.api.models.response.BaseResponse;
import com.diode.interview.api.models.response.TransformResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransformControllerTest {
    private static final String LONG_URL = "https://github.com/diodeme/interview-assignments/tree/master/java";
    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext context;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    public BaseResponse<TransformResult> mockHttpRequest(String url, Object request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MvcResult mvcResult = mockMvc.perform(post(url)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String json = mvcResult.getResponse().getContentAsString(Charsets.UTF_8);
            return objectMapper.readValue(json, new TypeReference<BaseResponse<TransformResult>>() {
            });
        } catch (Exception e) {
            log.error("mockHttpRequest error", e);
            return null;
        }
    }

    @Test
    public void testTransform() throws Exception {
        //生成短链接
        TransformLongRequest request = new TransformLongRequest();
        request.setUrl(LONG_URL);
        request.setAlg("MD5");
        request.setExpireSecs(100);
        BaseResponse<TransformResult> response = mockHttpRequest("/transform/shortURL/get", request);
        TransformResult data = response.getData();
        Assert.assertTrue(Objects.nonNull(data));
        String shortURL = data.getUrl();
        Assert.assertTrue(Objects.nonNull(shortURL));

        //生成长链接
        TransformShortRequest request1 = new TransformShortRequest();
        request1.setUrl(shortURL);
        BaseResponse<TransformResult> response1 = mockHttpRequest("/transform/longURL/get", request1);
        TransformResult data1 = response1.getData();
        Assert.assertTrue(Objects.nonNull(data1));
        String longURL1 = data1.getUrl();
        Assert.assertEquals(LONG_URL, longURL1);

        //是否走本地缓存
        BaseResponse<TransformResult> response2 = mockHttpRequest("/transform/shortURL/get", request);
        TransformResult data2 = response2.getData();
        Assert.assertTrue(Objects.nonNull(data2));
        String shortURL2 = data2.getUrl();
        Assert.assertTrue(Objects.nonNull(shortURL2));

        //测试完后应该删除测试数据，后续可优化
    }

    @Test
    public void testTransformNotExist() throws Exception {
        TransformShortRequest request = new TransformShortRequest();
        request.setUrl("aaaaaa");
        BaseResponse<TransformResult> response = mockHttpRequest("/transform/longURL/get", request);
        TransformResult data = response.getData();
        Assert.assertTrue(Objects.isNull(data));
    }

    @Test
    public void testTransformIllegal() throws Exception {
        //长链接不合法
        String longURL = "aaaaaa";
        TransformLongRequest request = new TransformLongRequest();
        request.setUrl(longURL);
        request.setAlg("MD5");
        request.setExpireSecs(100);
        BaseResponse<TransformResult> response = mockHttpRequest("/transform/shortURL/get", request);
        TransformResult data = response.getData();
        Assert.assertTrue(Objects.isNull(data));

        request.setUrl(LONG_URL);
        request.setAlg("alg");
        BaseResponse<TransformResult> response1 = mockHttpRequest("/transform/shortURL/get", request);
        TransformResult data1 = response1.getData();
        Assert.assertTrue(Objects.isNull(data1));

        request.setAlg("MD5");
        request.setExpireSecs(1000000);
        BaseResponse<TransformResult> response2 = mockHttpRequest("/transform/shortURL/get", request);
        TransformResult data2 = response2.getData();
        Assert.assertTrue(Objects.isNull(data2));

        //短链接不合法
        TransformShortRequest request3 = new TransformShortRequest();
        request3.setUrl("aaaaaaaaaaaa");
        BaseResponse<TransformResult> response3 = mockHttpRequest("/transform/longURL/get", request);
        TransformResult data3 = response3.getData();
        Assert.assertTrue(Objects.isNull(data3));
    }
}
