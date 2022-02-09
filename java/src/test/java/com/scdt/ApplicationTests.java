package com.scdt;

import com.alibaba.fastjson.JSONObject;
import com.scdt.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StopWatch;

@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
class ApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UrlService urlService;


    @Test
    void test1() throws Exception {
        String baseUrl = "https://www.baidu.com?params=" + 100;
        String content = getContent("http://localhost:8080/url/l2s?url=" + baseUrl);
        System.out.println("短域名：" + content);
        JSONObject obj = JSONObject.parseObject(content);
        String shortUrl = obj.getString("data");
        System.out.println("对应长域名：" + getContent("http://localhost:8080/url/s2l?url=" + shortUrl));
    }

    @Test
    void test2() throws Exception {
        StopWatch watch = new StopWatch("统计");
        for (int i = 0; i < 10000; i++) {
            String baseUrl = "https://www.baidu.com?params=" + i;
            watch.start("task_" + (watch.getTaskCount() + 1));
            String content = getContent("http://localhost:8080/url/l2s?url=" + baseUrl);
            System.out.println("短域名：" + content);
            JSONObject obj = JSONObject.parseObject(content);
            String shortUrl = obj.getString("data");
            System.out.println("对应长域名：" + getContent("http://localhost:8080/url/s2l?url=" + shortUrl));
            watch.stop();
        }
        System.out.println(watch.prettyPrint());

    }

    public String getContent(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
