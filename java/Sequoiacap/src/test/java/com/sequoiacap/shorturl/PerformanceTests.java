package com.sequoiacap.shorturl;

import com.sequoiacap.shorturl.controller.IndexController;
import com.sequoiacap.shorturl.service.UrlService;
import com.sequoiacap.shorturl.utils.BaseEncoder;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 性能测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceTests {

    @Autowired
    protected UrlService urlService;

    private MockMvc mockMvc;

    @Autowired
    private IndexController indexController;

    static AtomicLong counterPut = new AtomicLong(0l);
    static AtomicLong counterGet = new AtomicLong(0l);
    static AtomicLong counter = new AtomicLong(1l);
    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        int i = 1000;
        while(i>=0){
            long num = counterPut.getAndIncrement();
            urlService.getShortUrl("http://www.900.com/page_" + num);
            i--;
        }

    }


    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    /**
     * 测试存储
     * @throws Exception
     */
    @Test
    @PerfTest(invocations = 1000,threads = 100)
    public void perfTestPut() throws Exception {
       putUrl();
    }

    /**
     * 测试读取
     * @throws Exception
     */
    @Test
    @PerfTest(invocations = 1000,threads = 100)
    public void perfTestGet() throws Exception {
        getUrl();
    }

    /**
     * 存储读取混合
     * @throws Exception
     */
    @Test
    @PerfTest(invocations = 1000,threads = 100)
    public void perfMixRequest() throws Exception {
        long num = counter.getAndIncrement();
        if(num%5!=0){
            getUrl();
        }else{
            putUrl();
        }
    }

    protected void putUrl() throws Exception {
        long num = counterPut.getAndIncrement();

        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_short_url").param("url", "http://www.900.com/page_" + num))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void getUrl() throws Exception {
        long num = counterGet.getAndIncrement();

        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_url").param("key", BaseEncoder.encode(num)))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

}
