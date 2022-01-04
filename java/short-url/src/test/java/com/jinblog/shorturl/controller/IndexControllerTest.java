package com.jinblog.shorturl.controller;

import com.jinblog.shorturl.common.UrlUtil;
import com.jinblog.shorturl.config.ShortConfiguration;
import com.jinblog.shorturl.service.EventHandler;
import com.jinblog.shorturl.service.Generator;
import com.jinblog.shorturl.service.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.concurrent.ExecutorService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 指定profile
@Profile("default")
//@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {IndexController.class})
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Generator generator;
    @MockBean
    private Storage storage;

    @MockBean
    private EventHandler eventHandler;

    @MockBean
    @Qualifier("shortUrlEventHandlerPool")
    private ExecutorService shortUrlEventHandlerPool;

    @MockBean
    private ShortConfiguration shortConfiguration;

    @MockBean
    private UrlUtil urlUtil;

    public String longUrl;
    public String shortUrl;
    public String shortUrlDomain = "https://jin.com/";

    @BeforeEach
    public void beforeEach() {
        Mockito.when(shortConfiguration.getShortUrlDomain()).thenReturn(shortUrlDomain);
        Mockito.when(shortConfiguration.getUrlMaxLen()).thenReturn(8);
        longUrl = "https://www.xxx.com/aaa/bbb";
        StringBuilder shortUrlBuilder = new StringBuilder(shortConfiguration.getShortUrlDomain());
        for (int i = 0; i < shortConfiguration.getUrlMaxLen(); i++) {
            shortUrlBuilder.append("a");
        }
        shortUrl = shortUrlBuilder.toString();
    }

    @Test
    void longToShort() throws Exception {
        RequestBuilder request = null;
        Mockito.when(generator.generate()).thenReturn(shortUrl.replace(shortUrlDomain, ""));
        Mockito.when(shortConfiguration.getHighestMemoryPercent()).thenReturn(0.9);
        // 不带参数
        request = MockMvcRequestBuilders.get("/long-to-short")
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // 带上错误的参数
        request = MockMvcRequestBuilders.get("/long-to-short?url=htt://aa")
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // 带上正确的参数
        request = MockMvcRequestBuilders.get("/long-to-short?url=" + longUrl)
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(shortUrl))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // 带上正确的参数，但是内存使用率达到极限
        Mockito.when(shortConfiguration.getHighestMemoryPercent()).thenReturn(0.0);
        request = MockMvcRequestBuilders.get("/long-to-short?url=" + longUrl)
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // 带上正确的参数，但是已达到生成器最大值
        Mockito.when(shortConfiguration.getHighestMemoryPercent()).thenReturn(0.9);
        Mockito.when(generator.generate()).thenReturn(null);
        request = MockMvcRequestBuilders.get("/long-to-short?url=" + longUrl)
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.when(generator.generate()).thenReturn(shortUrl.replace(shortUrlDomain, ""));
    }

    @Test
    void shortToLong() throws Exception {
        RequestBuilder request = null;
        Mockito.when(storage.find(shortUrl.replace(shortUrlDomain, ""))).thenReturn(longUrl);
        Mockito.when(generator.validate(shortUrl)).thenReturn(true);
        // 带上正确的参数
        request = MockMvcRequestBuilders.get("/short-to-long?url=" + shortUrl)
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(longUrl))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.when(generator.validate(shortUrl)).thenReturn(false);
        // 带上错误的参数
        request = MockMvcRequestBuilders.get("/short-to-long?url=http://www.ccoa")
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.when(generator.validate(shortUrl)).thenReturn(false);
        // 不带参数
        request = MockMvcRequestBuilders.get("/short-to-long")
                .contentType(MediaType.ALL);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}