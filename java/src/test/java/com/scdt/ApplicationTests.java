package com.scdt;

import com.scdt.service.UrlService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicLong;

@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
class ApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UrlService urlService;

    @Test
    void testLongToShort() throws Exception {
        String baseUrl = "https://www.baidu.com";
        String content = getContent("/l2s?url=" + baseUrl);
        System.out.println("短域名：" + content);
        System.out.println("对应长域名：" + getContent("/s2l?url=" + content));

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
